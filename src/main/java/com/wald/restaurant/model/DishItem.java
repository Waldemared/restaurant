package com.wald.restaurant.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dish_item")
public class DishItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    private Dish dish;

    private Float quantity;
}
