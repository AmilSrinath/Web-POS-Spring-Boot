package lk.ama.pos.ama.repository;

import lk.ama.pos.ama.dto.SaleBillDTO;
import lk.ama.pos.ama.dto.SaleBillItemDTO;
import lk.ama.pos.ama.dto.SaleDTO;
import lk.ama.pos.ama.dto.SaleItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SaleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ================= INSERT SALE =================
    public int insertSale(SaleDTO dto) {
        String sql = "INSERT INTO sales " +
                "(user_id, sale_number, customer_id, payment_method, subtotal, tax_amount, " +
                "total_amount, amount_paid, change_amount, status, visible, notes, receipt_name, is_bill_printed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 'completed', 1, ?, ?, 0)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 1); // TODO: replace with logged-in user id
            ps.setString(2, dto.getSaleNumber());
            ps.setObject(3, dto.getCustomerId()); // nullable
            ps.setString(4, dto.getPaymentMethod());
            ps.setDouble(5, dto.getSubtotal());
            ps.setDouble(6, dto.getTaxAmount() != null ? dto.getTaxAmount() : 0.0);
            ps.setDouble(7, dto.getTotalAmount());
            ps.setDouble(8, dto.getAmountPaid());
            ps.setDouble(9, dto.getChangeAmount() != null ? dto.getChangeAmount() : 0.0);
            ps.setString(10, dto.getNotes());
            ps.setString(11, dto.getReceiptName());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    // ================= INSERT SALE ITEMS =================
    public void insertSaleItems(int saleId, List<SaleItemDTO> items) {
        String sql = "INSERT INTO sale_items " +
                "(user_id, sale_id, product_id, batch_id, quantity, unit_price, line_total, status, visible) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 'active', 1)";

        for (SaleItemDTO item : items) {
            jdbcTemplate.update(sql,
                    1, // TODO: replace with logged-in user id
                    saleId,
                    item.getProductId(),
                    item.getBatchId(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getLineTotal()
            );
        }
    }

    // ================= DEDUCT BATCH QUANTITY =================
    public void deductBatchQuantity(int batchId, double quantity) {
        String sql = "UPDATE product_batches SET quantity = quantity - ? WHERE product_batch_id = ?";
        jdbcTemplate.update(sql, quantity, batchId);
    }

    // ================= INSERT CREDIT SALE =================
    public void insertCreditSale(int saleId, int customerId, double creditAmount) {
        String sql = "INSERT INTO credit_sales " +
                "(user_id, sale_id, customer_id, credit_amount, amount_paid, status, visible) " +
                "VALUES (?, ?, ?, ?, 0.00, 'unpaid', 1)";
        jdbcTemplate.update(sql,
                1, // TODO: replace with logged-in user id
                saleId,
                customerId,
                creditAmount
        );
    }

    // ================= UPDATE CUSTOMER CREDIT BALANCE =================
    public void updateCustomerCredit(int customerId, double creditAmount) {
        String sql = "UPDATE customers SET credit_balance = credit_balance + ? WHERE customer_id = ?";
        jdbcTemplate.update(sql, creditAmount, customerId);
    }

    // ================= GENERATE SALE NUMBER =================
    public String generateSaleNumber() {
        String sql = "SELECT COUNT(*) FROM sales WHERE DATE(created_at) = CURDATE()";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return "SALE-" + java.time.LocalDate.now().toString().replace("-", "") + "-" + String.format("%04d", count + 1);
    }

    // ================= GET SALE BY ID =================
    public SaleBillDTO getSaleById(int saleId) {
        String sql = """
        SELECT
            s.sale_id, s.sale_number, s.payment_method, s.subtotal, s.tax_amount,
            s.total_amount, s.amount_paid, s.change_amount, s.notes, s.receipt_name,
            s.status, s.created_at,
            c.customer_id, c.name AS customer_name, c.phone AS customer_phone,
            c.credit_balance AS customer_credit_balance
        FROM sales s
        LEFT JOIN customers c ON s.customer_id = c.customer_id
        WHERE s.sale_id = ?
    """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            SaleBillDTO dto = new SaleBillDTO();
            dto.setSaleId(rs.getInt("sale_id"));
            dto.setSaleNumber(rs.getString("sale_number"));
            dto.setPaymentMethod(rs.getString("payment_method"));
            dto.setSubtotal(rs.getDouble("subtotal"));
            dto.setTaxAmount(rs.getDouble("tax_amount"));
            dto.setTotalAmount(rs.getDouble("total_amount"));
            dto.setAmountPaid(rs.getDouble("amount_paid"));
            dto.setChangeAmount(rs.getDouble("change_amount"));
            dto.setNotes(rs.getString("notes"));
            dto.setReceiptName(rs.getString("receipt_name"));
            dto.setStatus(rs.getString("status"));
            dto.setCreatedAt(rs.getString("created_at"));

            // Customer (nullable via LEFT JOIN)
            int customerId = rs.getInt("customer_id");
            if (!rs.wasNull()) {
                dto.setCustomerId(customerId);
                dto.setCustomerName(rs.getString("customer_name"));
                dto.setCustomerPhone(rs.getString("customer_phone"));
                dto.setCustomerCreditBalance(rs.getDouble("customer_credit_balance"));
            }

            return dto;
        }, saleId);
    }

    // ================= GET SALE ITEMS BY SALE ID =================
    public List<SaleBillItemDTO> getSaleItemsBySaleId(int saleId) {
        String sql = """
        SELECT
            si.sale_item_id, si.product_id, p.product_name_sinhala AS product_name,
            si.batch_id, pb.batch_number,
            si.quantity, si.unit_price, si.line_total
        FROM sale_items si
        LEFT JOIN products p ON si.product_id = p.product_id
        LEFT JOIN product_batches pb ON si.batch_id = pb.product_batch_id
        WHERE si.sale_id = ? AND si.visible = 1
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SaleBillItemDTO item = new SaleBillItemDTO();
            item.setSaleItemId(rs.getInt("sale_item_id"));
            item.setProductId(rs.getInt("product_id"));
            item.setProductName(rs.getString("product_name"));
            item.setBatchId(rs.getInt("batch_id"));
            item.setBatchNumber(rs.getString("batch_number"));
            item.setQuantity(rs.getDouble("quantity"));
            item.setUnitPrice(rs.getDouble("unit_price"));
            item.setLineTotal(rs.getDouble("line_total"));
            return item;
        }, saleId);
    }
}