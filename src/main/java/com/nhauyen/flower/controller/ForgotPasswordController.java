package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.User;
import com.nhauyen.flower.repository.UserRepository;
import com.nhauyen.flower.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
public class ForgotPasswordController {

    @Autowired private UserRepository userRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;

    // 1. Hiện trang nhập Email
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password";
    }

    // 2. Xử lý gửi OTP
    @PostMapping("/forgot_password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Tạo OTP 6 số ngẫu nhiên
            String otp = String.valueOf(new Random().nextInt(900000) + 100000);
            
            // Lưu OTP vào DB (tạm dùng trường resetPasswordToken)
            user.setResetPasswordToken(otp);
            userRepository.save(user);

            // Gửi email
            emailService.sendOtpEmail(email, otp);
            
            // Chuyển sang trang nhập OTP, gửi kèm email để biết của ai
            model.addAttribute("email", email);
            return "verify_otp";
        } else {
            model.addAttribute("error", "Email không tồn tại trong hệ thống!");
            return "forgot_password";
        }
    }

    // 3. Xử lý xác thực OTP
    @PostMapping("/verify_otp")
    public String verifyOtp(@RequestParam("email") String email, 
                            @RequestParam("otp") String otp, 
                            Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getResetPasswordToken() != null && user.getResetPasswordToken().equals(otp)) {
            // OTP đúng -> Chuyển sang trang đổi mật khẩu
            model.addAttribute("email", email); // Truyền email sang form đổi pass
            return "reset_password_form";
        } else {
            model.addAttribute("error", "Mã OTP không chính xác!");
            model.addAttribute("email", email);
            return "verify_otp"; // Ở lại trang nhập OTP
        }
    }

    // 4. Lưu mật khẩu mới
    @PostMapping("/reset_password")
    public String processResetPassword(@RequestParam("email") String email, 
                                       @RequestParam("password") String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(password)); // Mã hóa pass mới
            user.setResetPasswordToken(null); // Xóa OTP
            userRepository.save(user);
        }
        return "redirect:/login?reset_success";
    }
}