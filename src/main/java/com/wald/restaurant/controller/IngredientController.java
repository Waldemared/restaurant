package com.wald.restaurant.controller;

import com.wald.restaurant.model.Ingredient;
import com.wald.restaurant.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Comparator;
import java.util.List;


@Controller
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(final IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public String getAll(final Model model) {
        List<Ingredient> ingredients = ingredientService.findAll();
        ingredients.sort(Comparator.comparing(Ingredient::getName));
        model.addAttribute("ingredients", ingredients);
        return "ingredients";
    }


    @PostMapping("/ingredients")
    public RedirectView add(final RedirectAttributes attributes,
                            @RequestParam final String name,
                            @RequestParam(required = false) final Float quantity) {
        attributes.addFlashAttribute("info", ingredientService.add(name, quantity));
        return new RedirectView("/ingredients");
    }

    @GetMapping("/ingredients/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(id));
        return "ingredient_edit";
    }

    @PostMapping("/ingredients/{id}/update")
    public RedirectView update(RedirectAttributes attributes,
                               @PathVariable Integer id,
                               @RequestParam String name,
                               @RequestParam Float quantity) {
        attributes.addFlashAttribute("info", ingredientService.update(id, name, quantity));
        return new RedirectView("/ingredients/" + id);
    }

    @PostMapping("/ingredients/{id}/delete")
    public RedirectView delete(@PathVariable Integer id) {
        ingredientService.delete(id);
        return new RedirectView("/ingredients");
    }
}