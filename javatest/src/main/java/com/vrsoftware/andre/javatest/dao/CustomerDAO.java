package com.vrsoftware.andre.javatest.dao;

import com.vrsoftware.andre.javatest.config.AppConfig;
import com.vrsoftware.andre.javatest.domain.entity.Customer;
import com.vrsoftware.andre.javatest.domain.repository.CustomerRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author André Luiz Nunes Goulart
 */

public class CustomerDAO {

    private final CustomerRepository customerRepository;

    public CustomerDAO() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        customerRepository = context.getBean(CustomerRepository.class);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            customers = customerRepository.findAllCustomers();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        Customer savedCostumer = null;
        try {
            savedCostumer = customerRepository.saveAndFlush(customer);
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't save customer. - " + e.getMessage());
        }

        return savedCostumer;
    }

    public void delete(Long id) {
        try {
            customerRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
