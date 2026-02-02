package com.example.demo.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Product;

@Service
public class ProductService {
    private List<Product> listProduct = new ArrayList<>();

    public List<Product> getAll() { return listProduct; }
    public Product get(int id) { 
        return listProduct.stream().filter(p -> p.getId() == id).findFirst().orElse(null); 
    }
    public void add(Product p) {
        int maxId = listProduct.stream().mapToInt(Product::getId).max().orElse(0);
        p.setId(maxId + 1);
        listProduct.add(p);
    }
    public void update(Product editP) {
        Product find = get(editP.getId());
        if (find != null) {
            find.setName(editP.getName());
            find.setPrice(editP.getPrice());
            find.setCategory(editP.getCategory());
            if (editP.getImage() != null) find.setImage(editP.getImage());
        }
    }
    public void delete(int id) { listProduct.removeIf(p -> p.getId() == id); }

    public void updateImage(Product p, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Path dir = Paths.get("src/main/resources/static/images");
                if (!Files.exists(dir)) Files.createDirectories(dir);
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Files.copy(file.getInputStream(), dir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                p.setImage(fileName);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }
}