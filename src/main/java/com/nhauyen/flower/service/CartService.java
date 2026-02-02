package com.nhauyen.flower.service;

import com.nhauyen.flower.entity.Product;
import com.nhauyen.flower.model.CartItem;
import com.nhauyen.flower.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@SessionScope
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    private Map<Long, CartItem> items = new HashMap<>();

    public void add(Long id) {
        CartItem item = items.get(id);
        if (item != null) {
            item.setQuantity(item.getQuantity() + 1);
            items.put(id, item);
        } else {
            Product product = productRepository.findById(id).orElse(null);
            if (product != null) {
                // Sửa đoạn này: dùng product.getId()
                item = new CartItem(product.getId(), product.getName(), product.getPrice(), 1, product.getImage());
                items.put(id, item);
            }
        }
    }

    public void remove(Long id) {
        items.remove(id);
    }

    public void update(Long id, int quantity) {
        CartItem item = items.get(id);
        if (item != null) {
            item.setQuantity(quantity);
            items.put(id, item);
        }
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getAllItems() {
        return items.values();
    }

    public double getAmount() {
        return items.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();
    }
}