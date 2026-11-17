package fr.simplon.microcommerce.product.config;

import fr.simplon.microcommerce.product.entity.Product;
import fr.simplon.microcommerce.product.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductInitializer {

    @Bean
    CommandLineRunner initializeBooks(ProductService productService) {
        return args -> {
            Product product1 = productService
                    .createProduct(new Product(null, "978-3-16-148410-0", "ballon", "loisirs","ballon de baudruche"));
            Product product2 = productService.createProduct(new Product(null, "978-1-491-94726-3", "vélo", "sport","velo rouge VTT"));
            Product product3 = productService
                    .createProduct(new Product(null, "978-0-13-468599-1", "Livre Clean Architecture", "livre","livre sur le code"));

            System.out.println("Books initialized:");
            System.out.println("ID: " + product1.getId() + ", Nom: " + product1.getName());
            System.out.println("ID: " + product2.getId() + ", Nom: " + product2.getName());
            System.out.println("ID: " + product3.getId() + ", Nom: " + product3.getName());
        };
    }
}

