package com.example.toytroopers.model;

public class Category {
    private String categoryId;
    private String categoryImageURL;
    private String categoryName;

    public Category(String categoryId, String categoryImageURL, String categoryName) {
        this.categoryId = categoryId;
        this.categoryImageURL = categoryImageURL;
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryImageURL() {
        return categoryImageURL;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

