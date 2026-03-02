package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleBillDTO {
    // Sale info
    private Integer saleId;
    private String saleNumber;
    private String paymentMethod;
    private Double subtotal;
    private Double taxAmount;
    private Double totalAmount;
    private Double amountPaid;
    private Double changeAmount;
    private String notes;
    private String receiptName;
    private String status;
    private String createdAt;

    // Customer info (nullable)
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private Double customerCreditBalance;

    // Sale items
    private List<SaleBillItemDTO> items;
}