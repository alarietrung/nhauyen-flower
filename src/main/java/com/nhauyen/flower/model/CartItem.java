package com.nhauyen.flower.model;

public class CartItem {
    // Đổi tên thành 'id' cho giống với Product bên trang chủ để đỡ nhầm
    private Long id; 
    private String name;
    private double price;
    private int quantity = 1; 
    private String image;

    public CartItem() {
    }

    public CartItem(Long id, String name, double price, int quantity, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    // --- Getter & Setter (BẮT BUỘC PHẢI CÓ) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}