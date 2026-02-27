package lk.ama.pos.ama.repository;

import lk.ama.pos.ama.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(CustomerDTO customer) {
        String sql = "INSERT INTO customers (name, address, phone, user_id, credit_balance) VALUES (?, ?, ?, 1, ?)";
        return jdbcTemplate.update(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getCredit());
    }

    public int update(CustomerDTO customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, phone = ?, credit_balance = ? WHERE customer_id = ?";
        return jdbcTemplate.update(sql, customer.getName(), customer.getAddress(), customer.getPhone(), customer.getCredit(), customer.getCustomerId());
    }

    public int delete(Integer id) {
        String sql = "UPDATE customers SET is_active = 0 WHERE customer_id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public List<CustomerDTO> findAll() {
        String sql = "SELECT customer_id, name, address, phone, credit_balance FROM customers";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            CustomerDTO dto = new CustomerDTO();
            dto.setCustomerId(rs.getInt("customer_id"));
            dto.setName(rs.getString("name"));
            dto.setAddress(rs.getString("address"));
            dto.setPhone(rs.getString("phone"));
            dto.setCredit(rs.getDouble("credit_balance"));
            return dto;
        });
    }
}
