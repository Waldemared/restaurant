package com.wald.restaurant.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "restaurant", name = "dish_item")
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
