package com.dinsaren.bbuappserver.models;

import lombok.Data;
import javax.persistence.*;

@Table(name = "sms_product")
@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category_id")
    private int categoryId;
    private String name;
    @Column(name = "name_kh")
    private String nameKh;
    private String barcode;
    @Column(name = "qty_alter")
    private Double qtyAlter;
    @Column(name = "qty_on_hand")
    private Double qtyOnHand;
    private String status;
    private String image;

}
