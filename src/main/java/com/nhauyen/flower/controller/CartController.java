package com.nhauyen.flower.controller;

import com.nhauyen.flower.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cart") // Tất cả link bắt đầu bằng /cart sẽ vào đây
public class CartController {

    @Autowired
    private CartService cartService;

    // Xem giỏ hàng
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("items", cartService.getAllItems());
        model.addAttribute("total", cartService.getAmount());
        return "cart"; // Trả về file cart.html
    }

    // Thêm vào giỏ
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Long id) {
        cartService.add(id);
        return "redirect:/cart"; // Thêm xong chuyển hướng ngay tới trang giỏ hàng
    }

    // Xóa khỏi giỏ
    @GetMapping("/remove/{id}")
    public String removeFromCart(@PathVariable("id") Long id) {
        cartService.remove(id);
        return "redirect:/cart";
    }

    // Cập nhật số lượng (Khi người dùng chỉnh số lượng trong ô input)
    @GetMapping("/update")
    public String updateCart(@RequestParam("id") Long id, @RequestParam("qty") int qty) {
        cartService.update(id, qty);
        return "redirect:/cart";
    }
    
    // Xóa hết
    @GetMapping("/clear")
    public String clearCart() {
        cartService.clear();
        return "redirect:/cart";
    }
}