package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.dto.CreditSaleDetailDTO;
import lk.ama.pos.ama.service.CreditSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-sales")
@CrossOrigin(origins = "http://localhost:5173")
public class CreditSaleController {

    @Autowired
    private CreditSaleService creditSaleService;

    // GET /api/credit-sales/all
    @GetMapping("/all")
    public List<CreditSaleDetailDTO> getAll() {
        return creditSaleService.getAllCreditSales();
    }

    // GET /api/credit-sales/{id}
    @GetMapping("/{id}")
    public CreditSaleDetailDTO getById(@PathVariable int id) {
        return creditSaleService.getCreditSaleById(id);
    }

    // GET /api/credit-sales/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public List<CreditSaleDetailDTO> getByCustomer(@PathVariable int customerId) {
        return creditSaleService.getCreditSalesByCustomer(customerId);
    }
}