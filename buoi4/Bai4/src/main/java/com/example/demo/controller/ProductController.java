package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private CategoryService categoryService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(@Valid Product p, BindingResult res, @RequestParam("category.id") int cId, @RequestParam("imageProduct") MultipartFile file, Model model) {
        if (res.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }
        productService.updateImage(p, file);
        p.setCategory(categoryService.get(cId));
        productService.add(p);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product p = productService.get(id);
        if (p == null) return "redirect:/products";
        model.addAttribute("product", p);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Product p, BindingResult res, @RequestParam("category.id") int cId, @RequestParam("imageProduct") MultipartFile file, Model model) {
        if (res.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }
        if (!file.isEmpty()) productService.updateImage(p, file);
        p.setCategory(categoryService.get(cId));
        productService.update(p);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}