package com.wald.restaurant.Service;

import com.wald.restaurant.Model.Ingredient;
import com.wald.restaurant.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class IngredientService {
    private IngredientRepository ingredientRepository;

    public IngredientService(@Autowired IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient findById(Integer id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public Set<String> add(String name, Integer quantity) {
        Set<String> info = new HashSet<>();
        if (ingredientRepository.findByName(name).isPresent())
            info.add("exists");
        if (name.equals(""))
            info.add("name");
        if (quantity != null && quantity < 0)
            info.add("quantity");
        if (info.size() > 0)
            return info;
        info.add("success");
        Ingredient ingredient = new Ingredient();
        ingredient.setName(name);
        ingredient.setQuantity(quantity);
        ingredientRepository.save(ingredient);
        return info;
    }

    public Set<String> update(Integer id, String name, Integer quantity) {
        Set<String> info = new HashSet<>();
        Ingredient oldIngredient = ingredientRepository.findById(id).get();
        if (name.equals("")
            || ingredientRepository.findByName(name).isPresent()
            && ingredientRepository.findByName(name).get().getId() != id)
                info.add("name");
        if (quantity != null && quantity < 0)
            info.add("quantity");
        if (info.size() > 0)
            return info;
        info.add("success");
        oldIngredient.setName(name);
        oldIngredient.setQuantity(quantity);
        ingredientRepository.save(oldIngredient);
        return info;
    }

    public boolean delete(Integer id) {
        try {
            ingredientRepository.delete(ingredientRepository.findById(id).get());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }
}