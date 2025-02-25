package com.vrsoftware.andre.javatest.domain.repository;

import com.vrsoftware.andre.javatest.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT date, customer_id\n" +
            "FROM sales s\n" +
            "INNER JOIN customers c  ON s.customer_id  = c.id;", nativeQuery = true)
    List<Sale> findGroupByCustomer(Long id);

    @Query(value = "SELECT id, description, price\n" +
            "FROM products;", nativeQuery = true)
    List<Sale> findGroupByProduct(Long id);
}
