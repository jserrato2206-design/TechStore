package com.techstore.models;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    
    public User() {
        this.role = "user";
    }
    
    public User(int id, String name, String email, String phone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = "user";
    }
    
    public User(int id, String name, String email, String phone, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role != null ? role : "user";
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role != null ? role : "user";
    }
    
    public void setRole(String role) {
        this.role = role != null ? role : "user";
    }
    
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(getRole());
    }
}
