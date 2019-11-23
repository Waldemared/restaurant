package com.wald.restaurant.Controller;

import com.wald.restaurant.Model.Ingredient;
import com.wald.restaurant.Service.IngredientService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@Controller
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(@Autowired IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/ingredients")
    public String registerPage(Model model) {
        Object attr = model.getAttribute("info");
        System.out.println(Optional.ofNullable(attr).toString());
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredients";
    }


    @PostMapping("/ingredients")
    public RedirectView add(RedirectAttributes attributes,
                                 @RequestParam String name,
                                 @RequestParam(required = false) Integer quantity) {
        attributes.addFlashAttribute("info", ingredientService.add(name, quantity));
        RedirectView redirectView = new RedirectView("/ingredients");
        return redirectView;
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
                               @RequestParam Integer quantity) {
        attributes.addFlashAttribute("info", ingredientService.update(id, name, quantity));
        RedirectView redirectView = new RedirectView("/ingredients/" + id);
        return redirectView;
    }

    @PostMapping("/ingredients/{id}/delete")
    public RedirectView delete(@PathVariable Integer id) {
        ingredientService.delete(id);
        RedirectView redirectView = new RedirectView("/ingredients");
        return redirectView;
    }
}