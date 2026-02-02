package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.User;
import com.nhauyen.flower.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Hiện form đăng ký
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // 2. Xử lý đăng ký
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, 
                               @RequestParam("confirmPassword") String confirmPassword, 
                               Model model) {
        
        // Kiểm tra xem username đã tồn tại chưa
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Tên đăng nhập này đã có người dùng!");
            return "register";
        }

        // Kiểm tra mật khẩu nhập lại
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu nhập lại không khớp!");
            return "register";
        }

        // Mã hóa mật khẩu và lưu user (Role mặc định là USER)
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); 
        userRepository.save(user);

        return "redirect:/login?registered"; // Chuyển sang trang đăng nhập và báo thành công
    }
}