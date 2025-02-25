package com.vrsoftware.andre.javatest.domain.repository;

import com.vrsoftware.andre.javatest.domain.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {


//    List<Sale> findGroupByCustomer(Long id);
//    List<Sale> findGroupByProduct(Long id);
}
