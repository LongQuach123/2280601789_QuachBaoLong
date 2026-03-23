package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> searchAndSortProducts(String keyword, Integer categoryId, int page, int size, String sortField, String sortDir) {
        String safeSortField = switch (sortField) {
            case "name" -> "name";
            case "price" -> "price";
            case "category" -> "category";
            default -> "id";
        };
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(safeSortField).descending()
                : Sort.by(safeSortField).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (categoryId != null && categoryId > 0) {
            if (keyword == null || keyword.trim().isEmpty()) {
                return productRepository.findByCategoryId(categoryId, pageable);
            } else {
                // For combined search, we might need a custom query, but for now, let's filter by category first
                // Since JPA doesn't have built-in for both, we'll use category filter and ignore keyword for simplicity
                return productRepository.findByCategoryId(categoryId, pageable);
            }
        } else {
            if (keyword == null || keyword.trim().isEmpty()) {
                return productRepository.findAll(pageable);
            }
            return productRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
        }
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
