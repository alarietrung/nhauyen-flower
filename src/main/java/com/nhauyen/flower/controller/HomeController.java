package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.Product;
import com.nhauyen.flower.repository.CategoryRepository;
import com.nhauyen.flower.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Số sản phẩm trên mỗi trang (Anh có thể sửa số này tùy thích)
    private final int PAGE_SIZE = 12;

    // 1. Trang chủ (Có phân trang)
    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Product> productPage = productRepository.findAll(pageable);
        
        model.addAttribute("products", productPage.getContent()); // Danh sách hoa của trang hiện tại
        model.addAttribute("categories", categoryRepository.findAll());
        
        // Gửi thông tin phân trang sang HTML
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        
        return "index";
    }

    // 2. Tìm kiếm sản phẩm (Có phân trang)
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, 
                         @RequestParam(defaultValue = "0") int page, 
                         Model model) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Product> productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("keyword", keyword);
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        
        return "index";
    }

    // 3. Lọc theo danh mục (Có phân trang)
    @GetMapping("/category/{id}")
    public String filterByCategory(@PathVariable("id") Long id, 
                                   @RequestParam(defaultValue = "0") int page,
                                   Model model) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        Page<Product> productPage = productRepository.findByCategoryId(id, pageable);
        
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("categoryId", id); // Lưu lại ID danh mục để phân trang biết đang ở mục nào
        
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        
        return "index";
    }

    // 4. Chi tiết sản phẩm (Giữ nguyên)
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "detail";
    }
}