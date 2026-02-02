package com.nhauyen.flower.repository;

import com.nhauyen.flower.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    // Tìm theo email để gửi mã reset
    User findByEmail(String email); 
    
    // Tìm theo mã token để xác thực lúc đổi pass
    User findByResetPasswordToken(String token);
}