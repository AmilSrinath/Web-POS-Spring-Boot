package lk.ama.pos.ama.service;

import lk.ama.pos.ama.dto.CustomerDTO;
import lk.ama.pos.ama.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public String saveCustomer(CustomerDTO dto) {
        return customerRepository.save(dto) > 0 ? "Saved Successfully" : "Save Failed";
    }

    public String updateCustomer(CustomerDTO dto) {
        return customerRepository.update(dto) > 0 ? "Updated Successfully" : "Update Failed";
    }

    public String deleteCustomer(Integer id) {
        int result = customerRepository.delete(id);
        return result > 0 ? "Customer deactivated successfully" : "Customer not found";
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll();
    }
}
