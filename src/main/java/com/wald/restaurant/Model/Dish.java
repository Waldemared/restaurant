package com.wald.restaurant.Model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(schema = "restaurant", name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dish_name")
    private String name;

    @Column(name = "use_coef")
    private Float usageCoef;

    @ToString.Exclude
    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishItem> dishItems;
}
