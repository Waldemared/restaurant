package com.wald.restaurant.Service;

import com.wald.restaurant.Model.Ingredient;
import com.wald.restaurant.Model.Supplier;
import com.wald.restaurant.Repository.IngredientRepository;
import com.wald.restaurant.Repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final IngredientRepository ingredientRepository;

    public SupplierService(@Autowired SupplierRepository supplierRepository,
                           @Autowired IngredientRepository ingredientRepository) {
        this.supplierRepository = supplierRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId).get();
    }

    public Set<String> addSupplier(String name, LocalTime weekDaysBeginTime, LocalTime weekDaysEndTime,
                                   LocalTime weekEndsBeginTime, LocalTime weekEndsEndTime) {
        HashSet<String> info = new HashSet<>();
        Supplier supplier = new Supplier();
        if (name.equals("")) {
            info.add("name");
        } else if (supplierRepository.findByName(name).isPresent()) {
            info.add("exists");
        } else {
            supplier.setName(name);
        }
        if (weekDaysBeginTime == null || weekDaysEndTime == null || weekEndsBeginTime == null || weekEndsEndTime == null
                || !weekDaysEndTime.isAfter(weekDaysBeginTime) || !weekEndsEndTime.isAfter(weekEndsBeginTime)) {
            info.add("time");
        } else {
            supplier.setWeekDaysBeginTime(weekDaysBeginTime);
            supplier.setWeekDaysEndTime(weekDaysEndTime);
            supplier.setWeekEndsBeginTime(weekEndsBeginTime);
            supplier.setWeekEndsEndTime(weekEndsEndTime);
        }
        if (!info.isEmpty())
            return info;
        supplierRepository.save(supplier);
        info.add("success");
        return info;
    }

    public Set<String> updateSupplier(Integer supplierId, String name, LocalTime weekDaysBeginTime, LocalTime weekDaysEndTime,
                                   LocalTime weekEndsBeginTime, LocalTime weekEndsEndTime) {
        HashSet<String> info = new HashSet<>();
        Supplier supplier = supplierRepository.findById(supplierId).get();
        if (name.equals("")) {
            info.add("name");
        } else if (supplierRepository.findByName(name).isPresent()
                && !supplierRepository.findByName(name).get().getId().equals(supplierId)) {
            info.add("exists");
        } else {
            supplier.setName(name);
        }
        if (weekDaysBeginTime == null || weekDaysEndTime == null || weekEndsBeginTime == null || weekEndsEndTime == null
                || !weekDaysEndTime.isAfter(weekDaysBeginTime) || !weekEndsEndTime.isAfter(weekEndsBeginTime)) {
            info.add("time");
        } else {
            supplier.setWeekDaysBeginTime(weekDaysBeginTime);
            supplier.setWeekDaysEndTime(weekDaysEndTime);
            supplier.setWeekEndsBeginTime(weekEndsBeginTime);
            supplier.setWeekEndsEndTime(weekEndsEndTime);
        }
        if (!info.isEmpty())
            return info;
        supplierRepository.save(supplier);
        info.add("success");
        return info;
    }

    public Set<String> deleteSupplier(Integer supplierId) {
        HashSet<String> info = new HashSet<>();
        supplierRepository.delete(supplierRepository.findById(supplierId).get());
        info.add("success");
        return info;
    }

    public List<Ingredient> getFreeIngredientsForSupplier(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).get();
        return ingredientRepository.findAll().stream()
                .filter(ing -> !supplier.getIngredients().contains(ing))
                .sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
    }

    public Set<String> addIngredient(Integer supplierId, Integer ingredientId) {
        HashSet<String> info = new HashSet<>();
        Supplier supplier = supplierRepository.findById(supplierId).get();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        if (supplier.getIngredients().contains(ingredient)) {
            info.add("contains");
        } else {
            info.add("success");
            supplier.getIngredients().add(ingredient);
            ingredient.getSuppliers().add(supplier);
            supplierRepository.save(supplier);
        }
        return info;
    }

    public Set<String> deleteIngredient(Integer supplierId, Integer ingredientId) {
        HashSet<String> info = new HashSet<>();
        Supplier supplier = supplierRepository.findById(supplierId).get();
        Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
        info.add("success");
        supplier.getIngredients().remove(ingredient);
        ingredient.getSuppliers().remove(supplier);
        supplierRepository.save(supplier);
        return info;
    }
}
