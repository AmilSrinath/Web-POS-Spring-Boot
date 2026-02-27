package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDTO {
    private Integer batchId;
    private Integer productId;
    private String batchNumber;
    private Double quantity;
    private Double costPrice;
    private Double sellingPrice;
    private Double markupPercentage;
    private Integer isExpired;
    private Date expireDate;
}
