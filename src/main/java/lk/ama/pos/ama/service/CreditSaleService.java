package lk.ama.pos.ama.service;

import lk.ama.pos.ama.dto.CreditSaleDetailDTO;
import lk.ama.pos.ama.repository.CreditSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditSaleService {

    @Autowired
    private CreditSaleRepository creditSaleRepository;

    public List<CreditSaleDetailDTO> getAllCreditSales() {
        List<CreditSaleDetailDTO> list = creditSaleRepository.getAllCreditSales();
        list.forEach(dto -> dto.setItems(
                creditSaleRepository.getItemsBySaleId(dto.getSaleId())
        ));
        return list;
    }

    public CreditSaleDetailDTO getCreditSaleById(int creditSalesId) {
        CreditSaleDetailDTO dto = creditSaleRepository.getCreditSaleById(creditSalesId);
        dto.setItems(creditSaleRepository.getItemsBySaleId(dto.getSaleId()));
        return dto;
    }

    public List<CreditSaleDetailDTO> getCreditSalesByCustomer(int customerId) {
        List<CreditSaleDetailDTO> list = creditSaleRepository.getCreditSalesByCustomer(customerId);
        list.forEach(dto -> dto.setItems(
                creditSaleRepository.getItemsBySaleId(dto.getSaleId())
        ));
        return list;
    }
}