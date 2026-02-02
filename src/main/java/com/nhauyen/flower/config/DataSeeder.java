package com.nhauyen.flower.config;

import com.nhauyen.flower.entity.User;
import com.nhauyen.flower.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem đã có tài khoản admin chưa
        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            // Mã hóa mật khẩu '123' trước khi lưu
            admin.setPassword(passwordEncoder.encode("123")); 
            admin.setFullName("Quản Trị Viên");
            admin.setRole("ADMIN");
            
            userRepository.save(admin);
            System.out.println(">>> ĐÃ TẠO TÀI KHOẢN ADMIN MẶC ĐỊNH: admin / 123");
        }
    }
}