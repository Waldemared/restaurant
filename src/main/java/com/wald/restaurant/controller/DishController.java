package com.wald.restaurant.controller;

import com.wald.restaurant.model.Dish;
import com.wald.restaurant.model.DishItem;
import com.wald.restaurant.model.Ingredient;
import com.wald.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;

@Controller
public class DishController {
    private final DishService dishService;

    DishController(@Autowired DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public String getAll(Model model) {
        List<Dish> list = dishService.getAll();
        list.sort(Comparator.comparing(Dish::getName));
        model.addAttribute("dishes", list);
        return "dishes";
    }

    @GetMapping("/dishes/{dishId}")
    public String getAll(@PathVariable Integer dishId, Model model) {
        Dish dish = dishService.getById(dishId);
        List<Ingredient> freeIngredients = dishService.getFreeIngredientsForDish(dishId);
        freeIngredients.sort(Comparator.comparing(Ingredient::getName));
        model.addAttribute("dish", dish);
        model.addAttribute("freeIngredients", freeIngredients);
        return "dish_edit";
    }

    @PostMapping("/dishes/add")
    public RedirectView addDish(@RequestParam String name, @RequestParam Float usage, RedirectAttributes attributes) {
        attributes.addFlashAttribute("addInfo", dishService.addDish(name, usage));
        return new RedirectView("/dishes");
    }

    @PostMapping("/dishes/{dishId}/update")
    public RedirectView updateDish(@PathVariable Integer dishId, @RequestParam String name, @RequestParam Float usage, RedirectAttributes attributes) {
        attributes.addFlashAttribute("updateInfo", dishService.updateDish(dishId, name, usage));
        return new RedirectView("/dishes/" + dishId);
    }

    @PostMapping("/dishes/{dishId}/delete")
    public RedirectView deleteDish(@PathVariable Integer dishId, RedirectAttributes attributes) {
        attributes.addFlashAttribute("info", dishService.deleteDish(dishId));
        return new RedirectView("/dishes");
    }

    @GetMapping("/dishes/{dishId}/items/{itemId}")
    public String getItem(@PathVariable Integer dishId, @PathVariable Integer itemId, RedirectAttributes attributes) {
        DishItem dishItem = dishService.getItem(dishId, itemId);
        attributes.addFlashAttribute("dish", dishItem);
        return "dish_edit";
    }

    @PostMapping("/dishes/{dishId}/items/{itemId}/update")
    public RedirectView updateItem(@PathVariable Integer dishId, @PathVariable Integer itemId,
                                   @RequestParam Integer ingredientId, @RequestParam Float quantity, RedirectAttributes attributes) {
        attributes.addFlashAttribute("updateItemInfo", dishService.updateItem(dishId, itemId, ingredientId, quantity));
        return new RedirectView("/dishes/" + dishId);
    }

    @PostMapping("/dishes/{dishId}/items/{itemId}/delete")
    public RedirectView deleteItem(@PathVariable Integer dishId, @PathVariable Integer itemId, RedirectAttributes attributes) {
        attributes.addFlashAttribute("info", dishService.deleteItem(dishId, itemId));
        return new RedirectView("/dishes/" + dishId);
    }

    @PostMapping("/dishes/{dishId}/items")
    public RedirectView addItem(@PathVariable Integer dishId, @RequestParam Integer ingredientId,
                          @RequestParam Float quantity, RedirectAttributes attributes) {
        attributes.addFlashAttribute("addInfo", dishService.addItem(dishId, ingredientId, quantity));
        return new RedirectView("/dishes/" + dishId);
    }
}
