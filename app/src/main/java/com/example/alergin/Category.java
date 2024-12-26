package com.example.alergin;

public class Category {
    private int id;
    private String imageCode;
    private String name;

    // Constructor
    public Category(int id, String imageCode, String name) {
        this.id = id;
        this.imageCode = imageCode;
        this.name = name;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getName() {
        return name;
    }
}
