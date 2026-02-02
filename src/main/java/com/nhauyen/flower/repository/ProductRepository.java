package com.nhauyen.flower.repository;

import com.nhauyen.flower.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 1. Tìm hoa theo tên (Có phân trang)
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    // 2. Lọc hoa theo ID danh mục (Có phân trang)
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}