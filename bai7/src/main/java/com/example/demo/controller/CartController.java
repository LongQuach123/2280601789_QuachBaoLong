package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Account;
import com.example.demo.model.Order;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalAmount", cartService.getTotalAmount());
        model.addAttribute("cartCount", cartService.getTotalQuantity());
        return "cart/list";
    }

    @GetMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId, RedirectAttributes redirectAttributes) {
        cartService.addToCart(productId);
        redirectAttributes.addFlashAttribute("successMessage", "Đã thêm sản phẩm vào giỏ hàng.");
        return "redirect:/products";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Authentication authentication, RedirectAttributes redirectAttributes) {
        if (cartService.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giỏ hàng đang trống.");
            return "redirect:/cart";
        }

        Account account = null;
        if (authentication != null) {
            account = accountRepository.findByLoginName(authentication.getName()).orElse(null);
        }

        Order order = cartService.checkout(account);
        redirectAttributes.addFlashAttribute("lastOrderId", order != null ? order.getId() : null);
        return "redirect:/cart/success";
    }

    @GetMapping("/success")
    public String checkoutSuccess(Model model) {
        return "cart/success";
    }
}
