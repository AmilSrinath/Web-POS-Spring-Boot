package lk.ama.pos.ama.service;

import lk.ama.pos.ama.dto.SaleBillDTO;
import lk.ama.pos.ama.dto.SaleDTO;
import lk.ama.pos.ama.dto.SaleItemDTO;
import lk.ama.pos.ama.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Transactional
    public int createSale(SaleDTO dto) {
        // 1. Generate sale number
        if (dto.getSaleNumber() == null || dto.getSaleNumber().isEmpty()) {
            dto.setSaleNumber(saleRepository.generateSaleNumber());
        }

        // 2. Calculate totals FIRST (before insertSale)
        double subtotal = 0.0;
        for (SaleItemDTO item : dto.getSaleItems()) {
            double lineTotal = item.getQuantity() * item.getUnitPrice();
            item.setLineTotal(lineTotal);
            subtotal += lineTotal;
        }
        dto.setSubtotal(subtotal);

        double tax = dto.getTaxAmount() != null ? dto.getTaxAmount() : 0.0;
        dto.setTaxAmount(tax);
        dto.setTotalAmount(subtotal + tax);

        double paid = dto.getAmountPaid() != null ? dto.getAmountPaid() : 0.0;
        dto.setAmountPaid(paid);
        dto.setChangeAmount(Math.max(paid - dto.getTotalAmount(), 0.0));

        // 3. Now insert sale (all values are populated)
        int saleId = saleRepository.insertSale(dto);

        // 4. Insert items & deduct stock
        saleRepository.insertSaleItems(saleId, dto.getSaleItems());
        for (SaleItemDTO item : dto.getSaleItems()) {
            saleRepository.deductBatchQuantity(item.getBatchId(), item.getQuantity());
        }

        // 5. Credit handling
        if ("credit".equalsIgnoreCase(dto.getPaymentMethod()) && dto.getCustomerId() != null) {
            double creditAmount = dto.getTotalAmount() - paid;
            if (creditAmount > 0) {
                saleRepository.insertCreditSale(saleId, dto.getCustomerId(), creditAmount);
                saleRepository.updateCustomerCredit(dto.getCustomerId(), creditAmount);
            }
        }

        return saleId;
    }

    public SaleBillDTO getSaleById(int saleId) {
        SaleBillDTO sale = saleRepository.getSaleById(saleId);
        sale.setItems(saleRepository.getSaleItemsBySaleId(saleId));
        return sale;
    }
}