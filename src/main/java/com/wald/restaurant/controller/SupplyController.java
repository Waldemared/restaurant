package com.wald.restaurant.controller;

import com.wald.restaurant.model.Supply;
import com.wald.restaurant.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class SupplyController {
    private final SupplyService supplyService;

    public SupplyController(@Autowired SupplyService supplyService) {
        this.supplyService = supplyService;
    }

    @GetMapping("/supplies")
    public String getAll(Model model, @RequestParam(required = false) String viewType) {
        List<Supply> supplies;
        if (viewType == null) {
            viewType = "all";
        }
        switch (viewType) {
            case "published" :  supplies = supplyService.getAll().stream().filter(Supply::getPublished).collect(Collectors.toList()); break;
            case "unpublished" : supplies = supplyService.getAll().stream().filter(Predicate.not(Supply::getPublished)).collect(Collectors.toList()); break;
            default : supplies = supplyService.getAll();
        }
        supplies.sort(Comparator.comparing(Supply::getLatestDateTime).reversed());
        model.addAttribute("supplies", supplies);
        model.addAttribute("suppliers", supplyService.getSuppliers());
        return "supplies";
    }

    @PostMapping("/supplies")
    public RedirectView add(RedirectAttributes attributes,
                            @RequestParam Integer supplierId) {
        Supply supply = supplyService.addSupply(supplierId);
        return new RedirectView("/supplies/" + supply.getId());
    }

    @GetMapping("/supplies/{supplyId}")
    public String getSupply(Model model, @PathVariable Integer supplyId) {
        model.addAttribute("supply", supplyService.getSupply(supplyId));
        model.addAttribute("freeIngredients", supplyService.getFreeIngredients(supplyId));
        return "supply_edit";
    }

    @PostMapping("/supplies/{supplyId}/publish")
    public RedirectView publishSupply(RedirectAttributes attributes,
                                     @PathVariable Integer supplyId) {
        attributes.addFlashAttribute("publishInfo", supplyService.publish(supplyId));
        return new RedirectView("/supplies/");
    }

    @PostMapping("/supplies/{supplyId}/delete")
    public RedirectView deleteSupply(RedirectAttributes attributes,
                                     @PathVariable Integer supplyId) {
        attributes.addFlashAttribute("deleteInfo", supplyService.deleteSupply(supplyId));
        return new RedirectView("/supplies/");
    }

    @PostMapping("/supplies/{supplyId}/items")
    public RedirectView addItem(RedirectAttributes attributes, @PathVariable Integer supplyId,
                               @RequestParam Integer ingredientId, @RequestParam Float quantity) {
        attributes.addFlashAttribute("addInfo", supplyService.addItem(supplyId, ingredientId, quantity));
        return new RedirectView("/supplies/" + supplyId);
    }

    @PostMapping("/supplies/{supplyId}/items/{itemId}/update")
    public RedirectView updateItem(RedirectAttributes attributes, @PathVariable Integer supplyId,
                                   @PathVariable Integer itemId, @RequestParam Float quantity) {
        attributes.addFlashAttribute("updateInfo", supplyService.updateItem(supplyId, itemId, quantity));
        return new RedirectView("/supplies/" + supplyId);
    }

    @PostMapping("/supplies/{supplyId}/items/{itemId}/delete")
    public RedirectView updateItem(RedirectAttributes attributes, @PathVariable Integer supplyId, @PathVariable Integer itemId) {
        attributes.addFlashAttribute("deleteInfo", supplyService.deleteItem(supplyId, itemId));
        return new RedirectView("/supplies/" + supplyId);
    }
}
