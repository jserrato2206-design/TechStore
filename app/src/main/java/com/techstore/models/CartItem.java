package com.techstore.models;

public class CartItem {
    private int id;
    private int userId;
    private int productId;
    private int quantity;
    private String productName;
    private double price;
    private String image;
    
    public CartItem() {
    }
    
    public CartItem(int id, int userId, int productId, int quantity, 
                    String productName, double price, String image) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
        this.image = image;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public double getSubtotal() {
        return price * quantity;
    }
    
    public String getFormattedSubtotal() {
        return String.format("$%.2f", getSubtotal());
    }
}
