package com.wald.restaurant.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "supply_item")
@Data
public class SupplyItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supply supply;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;

    @Column(name = "ingredient_quantity")
    private Float quantity;
}
