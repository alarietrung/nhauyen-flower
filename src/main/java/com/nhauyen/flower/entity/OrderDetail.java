package com.nhauyen.flower.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity; // Số lượng mua
    private double price; // Giá tại thời điểm mua

    // Liên kết ngược về Đơn hàng (N-1)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Liên kết về Sản phẩm (N-1)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // --- Constructor ---
    public OrderDetail() {}

    public OrderDetail(Order order, Product product, int quantity, double price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    // --- Getter & Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}