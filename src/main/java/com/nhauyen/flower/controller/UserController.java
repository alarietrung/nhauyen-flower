package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.User;
import com.nhauyen.flower.repository.OrderRepository;
import com.nhauyen.flower.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired private UserRepository userRepository;
    @Autowired private OrderRepository orderRepository;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);
        
        // --- SỬA Ở ĐÂY: Lấy danh sách đơn hàng theo username ---
        model.addAttribute("orders", orderRepository.findByAccountUsername(username));
        // -------------------------------------------------------
        
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User updatedUser) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName());
        currentUser.setFullName(updatedUser.getFullName());
        userRepository.save(currentUser);
        return "redirect:/profile?success";
    }
}