package lk.ama.pos.ama.repository;

import lk.ama.pos.ama.dto.BatchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BatchRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveBatch(BatchDTO dto) {
        String sql = """
                INSERT INTO batches 
                (product_id, batch_number, quantity, cost_price, selling_price,
                 markup_percentage, is_expired, expire_date)
                VALUES (?,?,?,?,?,?,?,?)
                """;

        return jdbcTemplate.update(sql,
                dto.getProductId(),
                dto.getBatchNumber(),
                dto.getQuantity(),
                dto.getCostPrice(),
                dto.getSellingPrice(),
                dto.getMarkupPercentage(),
                dto.getIsExpired(),
                dto.getExpireDate()
        );
    }

    public int updateBatch(BatchDTO dto) {
        String sql = """
                UPDATE batches SET
                product_id=?,
                batch_number=?,
                quantity=?,
                cost_price=?,
                selling_price=?,
                markup_percentage=?,
                is_expired=?,
                expire_date=?
                WHERE batch_id=?
                """;

        return jdbcTemplate.update(sql,
                dto.getProductId(),
                dto.getBatchNumber(),
                dto.getQuantity(),
                dto.getCostPrice(),
                dto.getSellingPrice(),
                dto.getMarkupPercentage(),
                dto.getIsExpired(),
                dto.getExpireDate(),
                dto.getBatchId()
        );
    }

    public int deleteBatch(int batchId) {
        String sql = "UPDATE batches SET is_expired = 0 WHERE batch_id = ?";
        return jdbcTemplate.update(sql, batchId);
    }
}
