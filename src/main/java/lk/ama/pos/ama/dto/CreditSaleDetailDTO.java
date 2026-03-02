package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditSaleDetailDTO {
    // Credit sale info
    private Integer creditSalesId;
    private Double creditAmount;
    private Double amountPaid;
    private Double remainingBalance;
    private String status;
    private String dueDate;
    private String createdAt;

    // Sale info
    private Integer saleId;
    private String saleNumber;
    private String paymentMethod;
    private Double subtotal;
    private Double taxAmount;
    private Double totalAmount;
    private String saleDate;
    private String notes;

    // Customer info
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private Double customerTotalCreditBalance;

    // Sale items
    private List<SaleBillItemDTO> items;
}