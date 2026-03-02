package lk.ama.pos.ama.repository;

import lk.ama.pos.ama.dto.CreditSaleDetailDTO;
import lk.ama.pos.ama.dto.SaleBillItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CreditSaleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ================= GET ALL CREDIT SALES =================
    public List<CreditSaleDetailDTO> getAllCreditSales() {
        String sql = """
            SELECT
            cs.credit_sales_id, cs.credit_amount, cs.amount_paid AS credit_amount_paid,
            (cs.credit_amount - cs.amount_paid) AS remaining_balance,
            cs.status, cs.due_date, cs.created_at AS credit_created_at,
            s.sale_id, s.sale_number, s.payment_method,
            s.subtotal, s.tax_amount, s.total_amount,
            s.amount_paid AS sale_amount_paid,
            s.created_at AS sale_date, s.notes,
            c.customer_id, c.name AS customer_name,
            c.phone AS customer_phone, c.address AS customer_address,
            c.credit_balance AS customer_total_credit_balance
            FROM credit_sales cs
            JOIN sales s ON cs.sale_id = s.sale_id
            JOIN customers c ON cs.customer_id = c.customer_id
            WHERE cs.visible = 1
            ORDER BY cs.created_at DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs));
    }

    // ================= GET CREDIT SALE BY ID =================
    public CreditSaleDetailDTO getCreditSaleById(int creditSalesId) {
        String sql = """
            SELECT
                cs.credit_sales_id, cs.credit_amount, cs.amount_paid,
                (cs.credit_amount - cs.amount_paid) AS remaining_balance,
                cs.status, cs.due_date, cs.created_at AS credit_created_at,
                s.sale_id, s.sale_number, s.payment_method,
                s.subtotal, s.tax_amount, s.total_amount,
                s.created_at AS sale_date, s.notes,
                c.customer_id, c.name AS customer_name,
                c.phone AS customer_phone, c.address AS customer_address,
                c.credit_balance AS customer_total_credit_balance
            FROM credit_sales cs
            JOIN sales s ON cs.sale_id = s.sale_id
            JOIN customers c ON cs.customer_id = c.customer_id
            WHERE cs.credit_sales_id = ? AND cs.visible = 1
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapRow(rs), creditSalesId);
    }

    // ================= GET CREDIT SALES BY CUSTOMER =================
    public List<CreditSaleDetailDTO> getCreditSalesByCustomer(int customerId) {
        String sql = """
            SELECT
                cs.credit_sales_id, cs.credit_amount, cs.amount_paid,
                (cs.credit_amount - cs.amount_paid) AS remaining_balance,
                cs.status, cs.due_date, cs.created_at AS credit_created_at,
                s.sale_id, s.sale_number, s.payment_method,
                s.subtotal, s.tax_amount, s.total_amount,
                s.created_at AS sale_date, s.notes,
                c.customer_id, c.name AS customer_name,
                c.phone AS customer_phone, c.address AS customer_address,
                c.credit_balance AS customer_total_credit_balance
            FROM credit_sales cs
            JOIN sales s ON cs.sale_id = s.sale_id
            JOIN customers c ON cs.customer_id = c.customer_id
            WHERE cs.customer_id = ? AND cs.visible = 1
            ORDER BY cs.created_at DESC
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRow(rs), customerId);
    }

    // ================= GET SALE ITEMS =================
    public List<SaleBillItemDTO> getItemsBySaleId(int saleId) {
        String sql = """
            SELECT
                si.sale_item_id, si.product_id,
                p.product_name_sinhala AS product_name,
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

    // ================= SHARED ROW MAPPER =================
    private CreditSaleDetailDTO mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        CreditSaleDetailDTO dto = new CreditSaleDetailDTO();
        dto.setCreditSalesId(rs.getInt("credit_sales_id"));
        dto.setCreditAmount(rs.getDouble("credit_amount"));
        dto.setAmountPaid(rs.getDouble("sale_amount_paid"));
        dto.setRemainingBalance(rs.getDouble("remaining_balance"));
        dto.setStatus(rs.getString("status"));
        dto.setDueDate(rs.getString("due_date"));
        dto.setCreatedAt(rs.getString("credit_created_at"));

        dto.setSaleId(rs.getInt("sale_id"));
        dto.setSaleNumber(rs.getString("sale_number"));
        dto.setPaymentMethod(rs.getString("payment_method"));
        dto.setSubtotal(rs.getDouble("subtotal"));
        dto.setTaxAmount(rs.getDouble("tax_amount"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setSaleDate(rs.getString("sale_date"));
        dto.setNotes(rs.getString("notes"));

        dto.setCustomerId(rs.getInt("customer_id"));
        dto.setCustomerName(rs.getString("customer_name"));
        dto.setCustomerPhone(rs.getString("customer_phone"));
        dto.setCustomerAddress(rs.getString("customer_address"));
        dto.setCustomerTotalCreditBalance(rs.getDouble("customer_total_credit_balance"));

        return dto;
    }
}