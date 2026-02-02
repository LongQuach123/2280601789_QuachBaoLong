package com.example.demo.service;

import com.example.demo.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private List<Category> categories = new ArrayList<>();

    public CategoryService() {
        categories.add(new Category(1, "Điện thoại"));
        categories.add(new Category(2, "Laptop"));
        categories.add(new Category(3, "Phụ kiện"));
    }

    public List<Category> getAll() {
        return categories;
    }

    public Category get(int id) {
        return categories.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
