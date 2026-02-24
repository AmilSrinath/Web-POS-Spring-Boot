package lk.ama.pos.ama.service;

import lk.ama.pos.ama.dto.ProductDTO;
import lk.ama.pos.ama.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void saveProduct(ProductDTO dto) {
        productRepository.saveProduct(dto);
    }

    public void updateProduct(ProductDTO dto) {
        productRepository.updateProduct(dto);
    }

    public void deleteProduct(int productId) {
        productRepository.softDeleteProduct(productId);
    }
}
