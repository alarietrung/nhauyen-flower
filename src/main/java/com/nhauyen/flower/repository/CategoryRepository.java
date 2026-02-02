package com.nhauyen.flower.repository;

import com.nhauyen.flower.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // JpaRepository đã có sẵn các hàm: findAll(), findById(), save(), delete()...
    // Anh không cần viết gì thêm trong này cũng chạy được!
}