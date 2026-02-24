package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
    private Integer productId;
    private String productName;
    private String productBillName;
    private String barcode;
    private String unit;
    private String productDescription;
    private Integer isLowStockAlert;
    private Double lowStock;
    private List<BatchDTO> batchDTOS;
}
