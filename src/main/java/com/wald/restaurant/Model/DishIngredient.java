package com.wald.restaurant.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(schema = "restaurant", name = "dish_ingredient")
public class DishIngredient {
    @Column(name = "dish_id")
    private Integer dishId;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    private Float quantity;
}
