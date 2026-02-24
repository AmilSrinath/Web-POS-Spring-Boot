package lk.ama.pos.ama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDTO {
    private int batchId;
    private int productId;
    private String batchNumber;
    private double quantity;
    private double costPrice;
    private double sellingPrice;
    private double markupPercentage;
    private int isExpired;
    private Date expireDate;
}
