package com.wald.restaurant.Service;

import com.wald.restaurant.Model.Ingredient;
import com.wald.restaurant.Model.Supplier;
import com.wald.restaurant.Model.Supply;
import com.wald.restaurant.Model.SupplyItem;
import com.wald.restaurant.Repository.IngredientRepository;
import com.wald.restaurant.Repository.SupplierRepository;
import com.wald.restaurant.Repository.SupplyRepository;
import com.wald.restaurant.Security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplyService {
    private final SupplyRepository supplyRepository;
    private final SupplierRepository supplierRepository;
    private final IngredientRepository ingredientRepository;

    public SupplyService(@Autowired SupplyRepository supplyRepository,
                         @Autowired SupplierRepository supplierRepository,
                         @Autowired IngredientRepository ingredientRepository) {
        this.supplyRepository = supplyRepository;
        this.supplierRepository = supplierRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }

    public List<Supplier> getSuppliers() {
        return supplierRepository.findAll();
    }

    public Supply addSupply(Integer supplierId) {
        Supply supply = new Supply();
        Supplier supplier = supplierRepository.findById(supplierId).get();
        supply.setManager(((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getManager());
        supply.setSupplier(supplier);
        supply.setCreatedDateTime(LocalDateTime.now());
        supply.setPublished(false);
        return supplyRepository.save(supply);
    }

    public Supply getSupply(Integer supplyId) {
        return supplyRepository.findById(supplyId).get();
    }

    public List<Ingredient> getFreeIngredients(Integer supplyId) {
        Supply supply = supplyRepository.findById(supplyId).get();
        return supply.getSupplier().getIngredients().stream().filter(ingredient ->
            !supply.getSupplyItems().stream().anyMatch(supplyItem -> supplyItem.getIngredient().getId().equals(ingredient.getId()))
        ).collect(Collectors.toList());
    }

    public Set<String> publish(Integer supplyId) {
        HashSet<String> info = new HashSet<>();
        Supply supply = supplyRepository.findById(supplyId).get();
        if (supply.getSupplyItems().isEmpty()) {
            info.add("empty");
            return info;
        }
        supply.setManager(((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getManager());
        supply.setPublishedDateTime(LocalDateTime.now());
        supply.setPublished(true);
        supplyRepository.save(supply);
        ingredientRepository.saveAll(supply.getSupplyItems().stream().peek(item ->
                item.getIngredient().setQuantity(item.getQuantity() + item.getIngredient().getQuantity()))
                .map(SupplyItem::getIngredient).collect(Collectors.toList()));
        info.add("success");
        return info;
    }

    public Set<String> deleteSupply(Integer supplyId) {
        HashSet<String> info = new HashSet<>();
        Supply supply = supplyRepository.findById(supplyId).get();
        supplyRepository.delete(supply);
        info.add("success");
        return info;
    }

    public Set<String> addItem(Integer supplyId, Integer ingredientId, Float quantity) {
        HashSet<String> info = new HashSet<>();
        Supply supply = supplyRepository.findById(supplyId).get();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        if (supply.getSupplyItems().stream().anyMatch(item -> item.getIngredient().getId().equals(ingredient.getId()))) {
            info.add("exists");
        }
        if (quantity <= 0) {
            info.add("quantity");
        }
        if (!info.isEmpty()) {
            return info;
        }
        SupplyItem supplyItem = new SupplyItem();
        supplyItem.setSupply(supply);
        supplyItem.setIngredient(ingredient);
        supplyItem.setQuantity(quantity);
        supply.getSupplyItems().add(supplyItem);
        supplyRepository.save(supply);
        info.add("success");
        return info;
    }

    public Set<String> updateItem(Integer supplyId, Integer itemId, Float quantity) {
        HashSet<String> info = new HashSet<>();
        Supply supply = supplyRepository.findById(supplyId).get();
        SupplyItem supplyItem = supply.getSupplyItems().stream().filter(item -> item.getId().equals(itemId)).collect(Collectors.toList()).get(0);
        if (quantity <= 0) {
            info.add("quantity");
            return info;
        }
        supplyItem.setQuantity(quantity);
        supplyRepository.save(supply);
        info.add("success");
        return info;
    }

    public Set<String> deleteItem(Integer supplyId, Integer itemId) {
        HashSet<String> info = new HashSet<>();
        Supply supply = supplyRepository.findById(supplyId).get();
        SupplyItem supplyItem = supply.getSupplyItems().stream().filter(item -> item.getId().equals(itemId)).collect(Collectors.toList()).get(0);
        supply.getSupplyItems().remove(supplyItem);
        supplyRepository.save(supply);
        info.add("success");
        return info;
    }
}
