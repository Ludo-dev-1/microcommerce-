package fr.simplon.microcommerce.product.service;

import fr.simplon.microcommerce.product.client.InventoryClient;
import fr.simplon.microcommerce.product.entity.Product;
import fr.simplon.microcommerce.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryClient inventoryClient;

    public ProductService(ProductRepository productRepository, InventoryClient inventoryClient) {
        this.productRepository = productRepository;
        this.inventoryClient = inventoryClient;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        Product savedProduct = (Product) productRepository.save(product);
        inventoryClient.initializeStock(savedProduct.getId());

        return savedProduct;
    }
}
