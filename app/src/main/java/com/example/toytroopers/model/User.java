package com.example.toytroopers.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String contactNumber;
    private String email;
    private String password;
    private Map<String, Boolean> wishlist;
    private Map<String, Boolean> orders;
    private Map<String, Boolean> reviews;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String contactNumber, String email, String password) {
        this.contactNumber = contactNumber;
        this.email = email;
        this.password = password;
        this.wishlist = new HashMap<>();
        this.orders = new HashMap<>();
        this.reviews = new HashMap<>();
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Boolean> getWishlist() {
        return wishlist;
    }

    public void setWishlist(Map<String, Boolean> wishlist) {
        this.wishlist = wishlist;
    }

    public Map<String, Boolean> getOrders() {
        return orders;
    }

    public void setOrders(Map<String, Boolean> orders) {
        this.orders = orders;
    }

    public Map<String, Boolean> getReviews() {
        return reviews;
    }

    public void setReviews(Map<String, Boolean> reviews) {
        this.reviews = reviews;
    }
}