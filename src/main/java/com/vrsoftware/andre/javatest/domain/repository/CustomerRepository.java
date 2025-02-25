package com.vrsoftware.andre.javatest.domain.repository;

import com.vrsoftware.andre.javatest.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, Long>{

    @Query(value = "SELECT id, invoiceclosedate, name, purchaselimit\n" +
            "FROM customers order by id;", nativeQuery = true)
    List<Customer> findAllCustomers();
}
