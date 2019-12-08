package com.wald.restaurant.Controller;

import com.wald.restaurant.Model.Supplier;
import com.wald.restaurant.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Controller
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(@Autowired SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public String getAll(Model model) {
        List<Supplier> suppliers = supplierService.getAll();
        suppliers.sort(Comparator.comparing(Supplier::getName));
        model.addAttribute("suppliers", suppliers);
        return "suppliers";
    }

    @PostMapping("/suppliers")
    public RedirectView addSupplier(RedirectAttributes attributes, @RequestParam String name,
                                    @RequestParam LocalTime weekDaysBeginTime, @RequestParam LocalTime weekDaysEndTime,
                                    @RequestParam LocalTime weekEndsBeginTime, @RequestParam LocalTime weekEndsEndTime) {
        attributes.addFlashAttribute("addInfo", supplierService.addSupplier(name, weekDaysBeginTime, weekDaysEndTime, weekEndsBeginTime, weekEndsEndTime));
        return new RedirectView("/suppliers");
    }

    @GetMapping("/suppliers/{supplierId}")
    public String getSupplier(Model model, @PathVariable Integer supplierId) {
        Supplier supplier = supplierService.getSupplierById(supplierId);
        model.addAttribute("supplier", supplier);
        model.addAttribute("freeIngredients", supplierService.getFreeIngredientsForSupplier(supplierId));
        return "supplier_edit";
    }

    @PostMapping("/suppliers/{supplierId}/update")
    public RedirectView updateSupplier(RedirectAttributes attributes, @PathVariable Integer supplierId, @RequestParam String name,
                                       @RequestParam LocalTime weekDaysBeginTime, @RequestParam LocalTime weekDaysEndTime,
                                       @RequestParam LocalTime weekEndsBeginTime, @RequestParam LocalTime weekEndsEndTime) {
        attributes.addFlashAttribute("updateInfo", supplierService.updateSupplier(supplierId, name, weekDaysBeginTime, weekDaysEndTime, weekEndsBeginTime, weekEndsEndTime));
        return new RedirectView("/suppliers/" + supplierId);
    }

    @PostMapping("/suppliers/{supplierId}/delete")
    public RedirectView updateSupplier(RedirectAttributes attributes, @PathVariable Integer supplierId) {
        attributes.addFlashAttribute("info", supplierService.deleteSupplier(supplierId));
        return new RedirectView("/suppliers");
    }

    @PostMapping("/suppliers/{supplierId}/ingredients")
    public RedirectView addIngredient(RedirectAttributes attributes, @PathVariable Integer supplierId, @RequestParam Integer ingredientId) {
        attributes.addFlashAttribute("addInfo", supplierService.addIngredient(supplierId, ingredientId));
        return new RedirectView("/suppliers/" + supplierId);
    }

    @PostMapping("/suppliers/{supplierId}/ingredients/{ingredientId}/delete")
    public RedirectView deleteIngredient(RedirectAttributes attributes, @PathVariable Integer supplierId, @PathVariable Integer ingredientId) {
        attributes.addFlashAttribute("addInfo", supplierService.deleteIngredient(supplierId, ingredientId));
        return new RedirectView("/suppliers/" + supplierId);
    }
}
