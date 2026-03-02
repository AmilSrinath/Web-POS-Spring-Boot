package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.dto.SaleBillDTO;
import lk.ama.pos.ama.dto.SaleDTO;
import lk.ama.pos.ama.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleDTO dto) {
        int saleId = saleService.createSale(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Sale completed successfully",
                "saleId", saleId
        ));
    }

    @GetMapping("/{saleId}/bill")
    public ResponseEntity<?> getSaleBill(@PathVariable int saleId) {
        try {
            SaleBillDTO bill = saleService.getSaleById(saleId);
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("message", "Sale not found", "saleId", saleId));
        }
    }
}