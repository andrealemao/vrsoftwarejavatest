package com.vrsoftware.andre.javatest.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @OneToOne
    @JoinTable(
            joinColumns = @JoinColumn(name = "sale_item_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Product product;

    @Column
    private Integer quantity;
}
