package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.Category;
import com.nhauyen.flower.entity.Order;
import com.nhauyen.flower.entity.Product;
import com.nhauyen.flower.repository.CategoryRepository;
import com.nhauyen.flower.repository.OrderRepository;
import com.nhauyen.flower.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    // Đường dẫn lưu ảnh
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";

    // ==================== DASHBOARD (THỐNG KÊ) - CODE MỚI ====================
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 1. Đếm tổng số lượng
        long productCount = productRepository.count();
        long categoryCount = categoryRepository.count();
        long orderCount = orderRepository.count();

        // 2. Tính tổng doanh thu
        List<Order> allOrders = orderRepository.findAll();
        double totalRevenue = 0;
        for (Order order : allOrders) {
            // Chỉ tính tiền những đơn KHÔNG bị hủy (Tùy logic của anh, ở đây em tính hết cho đơn giản)
            if (!"ĐÃ HỦY".equals(order.getStatus())) {
                totalRevenue += order.getTotalAmount();
            }
        }

        // 3. Gửi số liệu sang giao diện
        model.addAttribute("productCount", productCount);
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("totalRevenue", totalRevenue);

        return "admin/dashboard";
    }

    // ==================== CÁC PHẦN CŨ GIỮ NGUYÊN BÊN DƯỚI ====================

    // --- Quản lý Sản phẩm ---
    @GetMapping("/products")
    public String listProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin/product-list";
    }

    @GetMapping("/products/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product-form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, imageFile.getOriginalFilename());
                if (!Files.exists(fileNameAndPath.getParent())) {
                    Files.createDirectories(fileNameAndPath.getParent());
                }
                Files.write(fileNameAndPath, imageFile.getBytes());
                product.setImage(imageFile.getOriginalFilename());
            }
            productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/product-form";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    // --- Quản lý Danh mục ---
    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/category-list";
    }

    @GetMapping("/categories/new")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category-form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        return "admin/category-form";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Không thể xóa danh mục đang có sản phẩm.");
        }
        return "redirect:/admin/categories";
    }

    // --- Quản lý Đơn hàng ---
    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "admin/order-list";
    }

    @GetMapping("/orders/view/{id}")
    public String viewOrder(@PathVariable("id") Long id, Model model) {
        Order order = orderRepository.findById(id).orElse(null);
        model.addAttribute("order", order);
        return "admin/order-detail";
    }

    @PostMapping("/orders/update-status/{id}")
    public String updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") String status) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
        return "redirect:/admin/orders/view/" + id;
    }
}