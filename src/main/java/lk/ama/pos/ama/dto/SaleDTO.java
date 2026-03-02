package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO {
    private Integer saleId;
    private String saleNumber;
    private Integer customerId;
    private String paymentMethod;   // "cash", "credit"
    private Double subtotal;
    private Double taxAmount;
    private Double totalAmount;
    private Double amountPaid;
    private Double changeAmount;
    private String notes;
    private String receiptName;
    private List<SaleItemDTO> saleItems;
}