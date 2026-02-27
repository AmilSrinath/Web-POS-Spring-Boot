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
                INSERT INTO product_batches 
                (product_id, batch_number, quantity, cost_price, selling_price,
                 markup_percentage, is_expired, expiry_date, user_id)
                VALUES (?,?,?,?,?,?,?,?, 1)
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
                UPDATE product_batches SET
                product_id=?,
                batch_number=?,
                quantity=?,
                cost_price=?,
                selling_price=?,
                markup_percentage=?,
                is_expired=?,
                expiry_date=?,
                user_id=1
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
        String sql = "UPDATE product_batches SET status = 0 WHERE product_batch_id = ?";
        return jdbcTemplate.update(sql, batchId);
    }
}
