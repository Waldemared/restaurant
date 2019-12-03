package com.wald.restaurant.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(schema = "restaurant", name = "dish")
public class Dish {
    @Id
    private Integer id;

    private String name;

    @Column(name = "use_coef")
    private Float usageCoef;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "dish_ingredient",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<DishIngredient> dishIngredients;
}
