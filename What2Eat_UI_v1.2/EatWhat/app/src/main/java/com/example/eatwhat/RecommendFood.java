package com.example.eatwhat;

public class RecommendFood {
    private String name;
    private int imageId;

    public RecommendFood(String name, int imageId){
        this.name = name;
        this.imageId = imageId;

    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
