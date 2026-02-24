package lk.ama.pos.ama.controller;

import lk.ama.pos.ama.dto.ProductDTO;
import lk.ama.pos.ama.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody ProductDTO dto) {
        productService.saveProduct(dto);
        return ResponseEntity.ok("Product saved successfully");
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody ProductDTO dto) {
        productService.updateProduct(dto);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
