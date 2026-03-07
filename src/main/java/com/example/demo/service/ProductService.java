package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Đổi int thành Long ở đây để hết lỗi findById
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Đổi int thành Long ở đây để hết lỗi deleteById
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}