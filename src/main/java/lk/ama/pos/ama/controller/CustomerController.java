package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.dto.CustomerDTO;
import lk.ama.pos.ama.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public String save(@RequestBody CustomerDTO customerDTO) {
        return customerService.saveCustomer(customerDTO);
    }

    @PutMapping("/update")
    public String update(@RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/all")
    public List<CustomerDTO> getAll() {
        return customerService.getAllCustomers();
    }
}
