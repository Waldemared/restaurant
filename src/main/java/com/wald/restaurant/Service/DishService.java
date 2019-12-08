package com.wald.restaurant.Service;


import com.wald.restaurant.Model.Dish;
import com.wald.restaurant.Model.DishItem;
import com.wald.restaurant.Model.Ingredient;
import com.wald.restaurant.Repository.DishRepository;
import com.wald.restaurant.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DishService {
    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishService(@Autowired DishRepository dishRepository, @Autowired IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public Dish getById(Integer dishId) {
        return dishRepository.findById(dishId).get();
    }

    public Set<String> addDish(String name, Float usage) {
        HashSet<String> info = new HashSet<>();
        if (dishRepository.findByName(name).isPresent())
            info.add("exists");
        if (name.equals(""))
            info.add("name");
        if (usage == null || usage <= 0)
            info.add("usage");
        if (info.size() > 0)
            return info;
        info.add("success");
        Dish dish = new Dish();
        dish.setName(name);
        dish.setUsageCoef(usage);
        dishRepository.save(dish);
        return info;
    }

    public Set<String> updateDish(Integer id, String name, Float usage) {
        HashSet<String> info = new HashSet<>();
        Dish dish = dishRepository.findById(id).get();
        if (name.equals("")
                || dishRepository.findByName(name).isPresent()
                && !dish.getName().equals(name)) {
            info.add("name");
        } else {
            dish.setName(name);
        }
        if (usage != null && usage < 0) {
            info.add("usage");
        } else {
            dish.setUsageCoef(usage);
        }
        if (info.size() > 0)
            return info;
        info.add("success");
        dishRepository.save(dish);
        return info;
    }

    public Set<String> deleteDish(Integer id) {
        HashSet<String> info = new HashSet<>();
        dishRepository.delete(dishRepository.findById(id).get());
        return info;
    }

    public DishItem getItem(Integer dishId, Integer itemId) {
        return dishRepository.findById(dishId).get().getDishItems().stream()
                .filter(item -> item.getId().equals(itemId)).collect(Collectors.toList()).get(0);
    }

    public Set<String> addItem(Integer dishId, Integer ingredientId, Float quantity) {
        Set<String> info = new HashSet<>();
        Dish dish = dishRepository.findById(dishId).get();
        DishItem dishItem = new DishItem();
        dishItem.setIngredient(ingredientRepository.findById(ingredientId).get());
        dishItem.setDish(dish);
        if (quantity <= 0 || 1000 - dish.getDishItems().stream().map(DishItem::getQuantity).reduce(0f, Float::sum) < quantity) {
            info.add("quantity");
        } else {
            dishItem.setQuantity(quantity);
        }
        if (!info.isEmpty()) {
            return info;
        }
        dish.getDishItems().add(dishItem);
        dishRepository.save(dish);
        info.add("success");
        return info;
    }

    public Set<String> updateItem(Integer dishId, Integer itemId, Integer ingredientId, Float quantity) {
        Set<String> info = new HashSet<>();
        Dish dish = dishRepository.findById(dishId).get();
        DishItem oldItem = dish.getDishItems().stream().filter(dishItem -> dishItem.getId()
                .equals(itemId)).collect(Collectors.toList()).get(0);
        oldItem.setIngredient(ingredientRepository.findById(ingredientId).get());
        if (quantity <= 0 || 1000 - dish.getDishItems().stream().map(DishItem::getQuantity).reduce(0f, Float::sum) + oldItem.getQuantity() < quantity) {
            info.add("quantity");
        } else {
            oldItem.setQuantity(quantity);
        }
        if (!info.isEmpty()) {
            return info;
        }
        info.add("success");
        dishRepository.save(dish);
        return info;
    }

    public Set<String> deleteItem(Integer dishId, Integer itemId) {
        Set<String> info = new HashSet<>();
        Dish dish = dishRepository.findById(dishId).get();
        DishItem dishItem = dish.getDishItems().stream().filter(item -> item.getId()
                .equals(itemId)).collect(Collectors.toList()).get(0);
        dish.getDishItems().remove(dishItem);
        info.add("success");
        dishRepository.save(dish);
        return info;
    }

    public List<Ingredient> getFreeIngredientsForDish(Integer dishId) {
        Dish dish = dishRepository.findById(dishId).get();
        List<Ingredient> ingredients = ingredientRepository.findAll().stream()
                .filter(ing -> !dish.getDishItems().stream().anyMatch(item -> item.getIngredient().getId().equals(ing.getId())))
                .collect(Collectors.toList());
        return ingredients;

    }
}
