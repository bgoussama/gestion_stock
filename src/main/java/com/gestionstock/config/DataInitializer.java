package com.gestionstock.config;

import com.gestionstock.model.Category;
import com.gestionstock.model.Product;
import com.gestionstock.model.Supplier;
import com.gestionstock.repository.CategoryRepository;
import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.SupplierRepository;
import com.gestionstock.service.AlertService;
import java.math.BigDecimal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seed(CategoryRepository categoryRepository, SupplierRepository supplierRepository,
            ProductRepository productRepository, AlertService alertService) {
        return args -> {
            if (productRepository.count() > 0) {
                return;
            }
            Category informatique = new Category();
            informatique.setName("Informatique");
            informatique.setDescription("Materiel informatique et accessoires");
            categoryRepository.save(informatique);

            Category bureau = new Category();
            bureau.setName("Bureau");
            bureau.setDescription("Fournitures de bureau");
            categoryRepository.save(bureau);

            Supplier supplier = new Supplier();
            supplier.setName("Atlas Distribution");
            supplier.setEmail("contact@atlas-distribution.ma");
            supplier.setPhone("+212 522 000 000");
            supplier.setAddress("Casablanca");
            supplierRepository.save(supplier);

            Product clavier = product("PRD-001", "Clavier mecanique", informatique, supplier, 25, 5,
                    new BigDecimal("349.00"));
            Product toner = product("PRD-002", "Toner imprimante", bureau, supplier, 3, 6, new BigDecimal("499.00"));
            productRepository.save(clavier);
            productRepository.save(toner);
            alertService.synchronize(clavier);
            alertService.synchronize(toner);
        };
    }

    private Product product(String reference, String name, Category category, Supplier supplier, int quantity,
            int threshold, BigDecimal price) {
        Product product = new Product();
        product.setReference(reference);
        product.setName(name);
        product.setDescription("Article courant du catalogue");
        product.setCategory(category);
        product.setSupplier(supplier);
        product.setQuantity(quantity);
        product.setAlertThreshold(threshold);
        product.setUnitPrice(price);
        return product;
    }
}
