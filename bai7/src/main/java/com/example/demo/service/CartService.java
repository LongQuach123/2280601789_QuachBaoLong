package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.model.Account;
import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.OrderDetail;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;

@Service
@SessionScope
public class CartService {
    private final Map<Long, CartItem> cartItems = new LinkedHashMap<>();

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    public void addToCart(Long productId) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return;
        }

        CartItem existingItem = cartItems.get(productId);
        if (existingItem == null) {
            cartItems.put(productId, new CartItem(product, 1));
        } else {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        }
    }

    public void updateQuantity(Long productId, int quantity) {
        if (!cartItems.containsKey(productId)) {
            return;
        }
        if (quantity <= 0) {
            cartItems.remove(productId);
            return;
        }
        cartItems.get(productId).setQuantity(quantity);
    }

    public void removeFromCart(Long productId) {
        cartItems.remove(productId);
    }

    public Collection<CartItem> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    public int getTotalQuantity() {
        return cartItems.values().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public long getTotalAmount() {
        return cartItems.values().stream().mapToLong(CartItem::getSubtotal).sum();
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    public void clearCart() {
        cartItems.clear();
    }

    public Order checkout(Account account) {
        if (cartItems.isEmpty()) {
            return null;
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setPaid(false);
        order.setAccount(account);
        order.setTotalAmount(getTotalAmount());

        for (CartItem cartItem : cartItems.values()) {
            Product product = productService.getProductById(cartItem.getProductId());
            if (product == null) {
                continue;
            }
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setPrice(cartItem.getPrice());
            detail.setQuantity(cartItem.getQuantity());
            order.getOrderDetails().add(detail);
        }

        Order savedOrder = orderRepository.save(order);
        clearCart();
        return savedOrder;
    }
}
