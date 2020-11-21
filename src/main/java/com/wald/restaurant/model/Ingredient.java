package com.wald.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ingredient_name")
    private String name;

    private Float quantity;

    @ManyToMany
    @JoinTable(name = "supplier_ingredient",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id"))
    private List<Supplier> suppliers;

    @ToString.Exclude
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DishItem> dishItems;

    @ToString.Exclude
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplyItem> supplyItems;

    @Transient
    private Float estimatedBalanceViaDays;

    public List<SupplyItem> getPublishedSupplyItems() {
        return this.supplyItems.stream().filter(supplyItem -> supplyItem.getSupply().getPublished()).collect(Collectors.toList());
    }
}
