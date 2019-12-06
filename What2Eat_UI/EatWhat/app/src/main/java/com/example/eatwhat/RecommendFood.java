package com.example.eatwhat;

import java.io.Serializable;

//推荐的食物
public class RecommendFood implements Serializable {
    private int imageId;
    private String name;
    private String detail;

    public RecommendFood(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId(){ return imageId; }
    public void setImageIdg(int img){ this.imageId=img; }

    public String getName(){ return name; }
    public void setName(String name){ this.name=name; }

    public String getDetail(){ return detail; }
    public void setDetail(String Detail){ this.detail=detail; }

}
