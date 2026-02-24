package lk.ama.pos.ama.repository;

import lk.ama.pos.ama.dto.BatchDTO;
import lk.ama.pos.ama.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductDTO> getAllProducts() {

        String productSql = "SELECT * FROM products WHERE visible = 1";

        List<ProductDTO> products = jdbcTemplate.query(productSql, (rs, rowNum) -> {
            ProductDTO product = new ProductDTO();
            product.setProductId(rs.getInt("product_id"));
            product.setProductName(rs.getString("name"));
            product.setBarcode(rs.getString("barcode"));
            product.setUnit(rs.getString("unit"));
            product.setProductBillName(rs.getString("product_name_sinhala"));
            product.setProductDescription(rs.getString("description"));
            product.setIsLowStockAlert(rs.getInt("is_low_stock_alert"));
            product.setLowStock(rs.getDouble("low_stock_threshold"));
            return product;
        });

        for (ProductDTO product : products) {
            String batchSql = "SELECT * FROM product_batches WHERE product_id = ? AND visible = 1";

            List<BatchDTO> batches = jdbcTemplate.query(batchSql,
                    new Object[]{product.getProductId()},
                    (rs, rowNum) -> new BatchDTO(
                            rs.getInt("product_batch_id"),
                            rs.getInt("product_id"),
                            rs.getString("batch_number"),
                            rs.getDouble("quantity"),
                            rs.getDouble("cost_price"),
                            rs.getDouble("selling_price"),
                            rs.getDouble("markup_percentage"),
                            rs.getInt("is_expired"),
                            rs.getDate("expiry_date")
                    ));

            product.setBatchDTOS(batches);
        }

        return products;
    }

    // ================= SAVE =================
    public void saveProduct(ProductDTO dto) {

        String sql = "INSERT INTO products " +
                "(user_id, name, barcode, description, is_low_stock_alert, low_stock_threshold, " +
                "status, visible, category_id, unit, product_name_sinhala) " +
                "VALUES (?, ?, ?, ?, ?, ?, 1, 1, ?, ?, ?)";

        jdbcTemplate.update(sql,
                1, // TODO: logged user id
                dto.getProductName(),
                dto.getBarcode(),
                dto.getProductDescription(),
                dto.getIsLowStockAlert(),
                dto.getLowStock(),
                1, // category_id (change if needed)
                dto.getUnit(),
                dto.getProductBillName()
        );
    }

    // ================= UPDATE =================
    public void updateProduct(ProductDTO dto) {

        String sql = "UPDATE products SET " +
                "name = ?, " +
                "barcode = ?, " +
                "description = ?, " +
                "is_low_stock_alert = ?, " +
                "low_stock_threshold = ?, " +
                "updated_at = CURRENT_TIMESTAMP " +
                "WHERE product_id = ? AND status = 1";

        jdbcTemplate.update(sql,
                dto.getProductName(),
                dto.getBarcode(),
                dto.getProductDescription(),
                dto.getIsLowStockAlert(),
                dto.getLowStock(),
                dto.getProductId()
        );
    }

    // ================= SOFT DELETE =================
    public void softDeleteProduct(int productId) {

        String sql = "UPDATE products SET status = 0 WHERE product_id = ?";
        jdbcTemplate.update(sql, productId);
    }
}
