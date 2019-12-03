package com.wald.restaurant.Repository;

import com.wald.restaurant.Model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    public Optional<Dish> findById(Integer id);
    public Set<Dish> findBy
}
