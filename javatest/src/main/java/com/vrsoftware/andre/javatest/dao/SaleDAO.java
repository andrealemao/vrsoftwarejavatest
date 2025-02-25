package com.vrsoftware.andre.javatest.dao;

import com.vrsoftware.andre.javatest.config.AppConfig;
import com.vrsoftware.andre.javatest.domain.entity.Sale;
import com.vrsoftware.andre.javatest.domain.repository.SaleRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class SaleDAO {
    private final SaleRepository saleRepository;

    public SaleDAO() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        this.saleRepository = context.getBean(SaleRepository.class);
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();

        try {
            sales = saleRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return sales;
    }

    public Sale save(Sale sale) {
        Sale savedSale = null;
        try {
            savedSale = saleRepository.saveAndFlush(sale);
        } catch (Exception e) {
            System.out.println("ERROR: Couldn't save sale. - " + e.getMessage());
        }

        return savedSale;
    }

    public void delete(Long id) {
        try {
            saleRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
