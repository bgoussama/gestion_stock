package com.gestionstock.controller;

import com.gestionstock.model.Product;
import com.gestionstock.repository.CategoryRepository;
import com.gestionstock.repository.ProductRepository;
import com.gestionstock.repository.SupplierRepository;
import com.gestionstock.service.AlertService;
import com.gestionstock.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final AlertService alertService;
    private final ProductService productService;

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository,
            SupplierRepository supplierRepository, AlertService alertService, ProductService productService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.alertService = alertService;
        this.productService = productService;
    }

    @GetMapping
    String list(@RequestParam(defaultValue = "") String q, Model model) {
        var products = q.isBlank() ? productRepository.findAll()
                : productRepository.findByNameContainingIgnoreCaseOrReferenceContainingIgnoreCase(q, q);
        model.addAttribute("products", products);
        model.addAttribute("q", q);
        return "products/list";
    }

    @GetMapping("/new")
    String create(Model model) {
        model.addAttribute("product", new Product());
        addFormData(model);
        return "products/form";
    }

    @GetMapping("/{id}/edit")
    String edit(@PathVariable Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id).orElseThrow());
        addFormData(model);
        return "products/form";
    }

    @PostMapping
    String save(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            addFormData(model);
            return "products/form";
        }
        var saved = productRepository.save(product);
        alertService.synchronize(saved);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Produit supprime avec succes.");
        return "redirect:/products";
    }

    private void addFormData(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("suppliers", supplierRepository.findAll());
    }
}
