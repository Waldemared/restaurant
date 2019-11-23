package com.wald.restaurant.Repository;

import com.wald.restaurant.Model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    public List<Ingredient> findIngredientByLastSupplyDateBetween(Date before, Date after);
    public Optional<Ingredient> findByName(String name);
}
