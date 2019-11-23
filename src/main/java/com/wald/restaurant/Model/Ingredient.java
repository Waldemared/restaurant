package com.wald.restaurant.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "ingredient", schema = "restaurant")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ingredient_name")
    private String name;

    private Integer quantity;

    @Column(name = "last_supply_date")
    private Date lastSupplyDate;
}
