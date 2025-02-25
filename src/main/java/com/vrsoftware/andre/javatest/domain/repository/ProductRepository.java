package com.vrsoftware.andre.javatest.domain.repository;

import com.vrsoftware.andre.javatest.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT id, description, price\n" +
            "FROM products;", nativeQuery = true)
    List<Product> findAllProducts();
}
