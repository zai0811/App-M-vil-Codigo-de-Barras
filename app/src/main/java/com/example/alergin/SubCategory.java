package com.example.alergin;
public class SubCategory {
    private int id;
    private String name;
    private String imageCode;
    private String videoURL;

    // Constructor
    public SubCategory(int id, String name, String imageCode, String videoURL) {
        this.id = id;
        this.name = name;
        this.imageCode = imageCode;
        this.videoURL = videoURL;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
