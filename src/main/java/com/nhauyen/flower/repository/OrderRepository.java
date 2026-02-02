package com.nhauyen.flower.repository;

import com.nhauyen.flower.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm đơn hàng theo tên tài khoản (Username)
    List<Order> findByAccountUsername(String accountUsername);
}