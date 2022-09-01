package com.dinsaren.bbuappserver.models;

import lombok.Data;

import javax.persistence.*;

@Table(name = "sms_unit_type")
@Data
@Entity
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String nameKh;
    private Double qty;
    private String status;

}
