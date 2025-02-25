package com.vrsoftware.andre.javatest.dao;

import com.vrsoftware.andre.javatest.config.AppConfig;
import com.vrsoftware.andre.javatest.domain.entity.Product;
import com.vrsoftware.andre.javatest.domain.repository.ProductRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final ProductRepository productRepository;

    public ProductDAO() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        productRepository = context.getBean(ProductRepository.class);
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            products = productRepository.findAllProducts();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return products;
    }

    public Product save(Product product) {
        Product savedProduct = null;
        try {
            savedProduct = productRepository.saveAndFlush(product);
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't save product. - " + e.getMessage());
        }

        return savedProduct;
    }

    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
