package com.wald.restaurant.repository;
import com.wald.restaurant.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {
    public Optional<Dish> findById(Integer id);
    public Optional<Dish> findByName(String name);
}
