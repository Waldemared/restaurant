package com.wald.restaurant.Model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(schema = "restaurant", name = "supply_item")
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
