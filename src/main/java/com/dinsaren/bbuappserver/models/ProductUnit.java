package com.dinsaren.bbuappserver.models;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sms_product_unit")
@Data
@Entity
public class ProductUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "unit_type_id")
    private int unitTypeId;
    private Double qtyAlter;
    @Column(name = "price")
    private Double price;
    @Column(name = "cost")
    private Double cost;
    private String status;
    private String image;

}
