package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

@Component
public class StringToCategoryConverter implements Converter<String, Category> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public Category convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        try {
            return categoryService.getCategoryById(Integer.parseInt(source));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
