package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@Service
public class ProductService {

    private final List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() {
        return listProduct;
    }

    public Product get(int id) {
        return listProduct.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product newProduct) {
        int maxId = listProduct.stream()
                .mapToInt(Product::getId)
                .max()
                .orElse(0);

        newProduct.setId(maxId + 1);
        listProduct.add(newProduct);
    }

    public void update(Product editProduct) {
        Product find = get(editProduct.getId());
        if (find != null) {
            find.setName(editProduct.getName());
            find.setPrice(editProduct.getPrice());
            find.setCategory(editProduct.getCategory());
            if (editProduct.getImage() != null) {
                find.setImage(editProduct.getImage());
            }
        }
    }

    public void updateImage(Product product, MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) return;

        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path uploadDir = Paths.get("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Files.copy(imageFile.getInputStream(),
                    uploadDir.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            product.setImage(fileName);

        } catch (IOException e) {
            throw new RuntimeException("Upload image failed", e);
        }
    }
}
