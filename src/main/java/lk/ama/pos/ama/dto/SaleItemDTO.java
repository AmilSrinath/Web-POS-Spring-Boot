package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleItemDTO {
    private Integer saleItemId;
    private Integer saleId;
    private Integer productId;
    private Integer batchId;
    private Double quantity;
    private Double unitPrice;
    private Double lineTotal;
}