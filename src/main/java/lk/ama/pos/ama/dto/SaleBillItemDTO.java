package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleBillItemDTO {
    private Integer saleItemId;
    private Integer productId;
    private String productName;
    private Integer batchId;
    private String batchNumber;
    private Double quantity;
    private Double unitPrice;
    private Double lineTotal;
}