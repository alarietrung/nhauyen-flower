package com.nhauyen.flower.controller;

import com.nhauyen.flower.entity.Order;
import com.nhauyen.flower.entity.OrderDetail;
import com.nhauyen.flower.entity.Product;
import com.nhauyen.flower.model.CartItem;
import com.nhauyen.flower.repository.OrderDetailRepository;
import com.nhauyen.flower.repository.OrderRepository;
import com.nhauyen.flower.repository.ProductRepository;
import com.nhauyen.flower.service.CartService;
import com.nhauyen.flower.service.EmailService; // Import EmailService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList; // Nhớ import cái này
import java.util.List;      // Nhớ import cái này

@Controller
public class OrderController {

    @Autowired private CartService cartService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private ProductRepository productRepository;
    
    @Autowired private EmailService emailService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        if (cartService.getAllItems().isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("items", cartService.getAllItems());
        model.addAttribute("total", cartService.getAmount());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String submitCheckout(@RequestParam("name") String name,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("address") String address,
                                 @RequestParam("note") String note) {

        Order order = new Order();
        order.setCustomerName(name);
        order.setCustomerPhone(phone);
        order.setCustomerAddress(address);
        order.setNote(note);
        order.setTotalAmount(cartService.getAmount());
        order.setStatus("CHỜ DUYỆT");

        // Gắn tài khoản vào đơn hàng (nếu đã đăng nhập)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            order.setAccountUsername(auth.getName());
        }

        Order savedOrder = orderRepository.save(order);

        // --- SỬA ĐOẠN NÀY ---
        // Tạo một list để chứa các món hàng, chuẩn bị đưa cho EmailService
        List<OrderDetail> detailsList = new ArrayList<>();

        for (CartItem item : cartService.getAllItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(savedOrder);
            Product product = productRepository.findById(item.getId()).orElse(null);
            detail.setProduct(product);
            detail.setPrice(item.getPrice());
            detail.setQuantity(item.getQuantity());
            
            orderDetailRepository.save(detail); // Lưu xuống DB
            
            detailsList.add(detail); // Thêm vào list tạm
        }

        // Cập nhật list chi tiết vào đơn hàng (để EmailService đọc được)
        savedOrder.setOrderDetails(detailsList);
        // ---------------------

        // Gửi email xác nhận
        emailService.sendOrderConfirmation(savedOrder); 

        cartService.clear();
        return "redirect:/order-success";
    }

    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }
}