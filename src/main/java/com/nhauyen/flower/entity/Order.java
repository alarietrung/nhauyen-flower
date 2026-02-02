package com.nhauyen.flower.entity;

import jakarta.persistence.*;
import java.util.ArrayList; // Nhớ import
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountUsername;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String note;
    private double totalAmount;
    private String status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // SỬA LỖI Ở ĐÂY: Luôn khởi tạo ArrayList để không bao giờ bị Null
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>(); 

    public Order() {
        this.createdAt = new Date();
        this.status = "CHỜ DUYỆT";
        this.orderDetails = new ArrayList<>(); // Chắc ăn lần 2
    }

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getAccountUsername() { return accountUsername; }
    public void setAccountUsername(String accountUsername) { this.accountUsername = accountUsername; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) { this.orderDetails = orderDetails; }
}