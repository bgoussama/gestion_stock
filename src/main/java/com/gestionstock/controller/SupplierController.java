package com.gestionstock.controller;

import com.gestionstock.model.Supplier;
import com.gestionstock.repository.SupplierRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping
    String list(Model model) {
        model.addAttribute("suppliers", supplierRepository.findAll());
        model.addAttribute("supplier", new Supplier());
        return "suppliers/list";
    }

    @PostMapping
    String save(@Valid @ModelAttribute Supplier supplier, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("suppliers", supplierRepository.findAll());
            return "suppliers/list";
        }
        supplierRepository.save(supplier);
        return "redirect:/suppliers";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable Long id) {
        supplierRepository.deleteById(id);
        return "redirect:/suppliers";
    }
}
