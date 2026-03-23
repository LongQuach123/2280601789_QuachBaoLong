package com.example.demo.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItem implements Serializable {
    private Long productId;
    private String productName;
    private String image;
    private long price;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.image = product.getImage();
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public long getSubtotal() {
        return price * quantity;
    }
}
