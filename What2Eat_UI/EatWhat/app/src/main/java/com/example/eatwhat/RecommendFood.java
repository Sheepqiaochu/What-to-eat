package com.example.eatwhat;

import java.io.Serializable;

//推荐的食物
public class RecommendFood implements Serializable {
    private int imageId;    //图片源
    private String name;    //名称
    private String detail;  //详细介绍
    private int price;      //价格
    private int excellence; //推荐度,满分100

    public RecommendFood(String name,String detail, int imageId,int price,int excellence){
        this.name = name;
        this.detail=detail;
        this.imageId = imageId;
        this.price=price;
        this.excellence=excellence;
    }

    public int getImageId(){ return imageId; }
    public void setImageIdg(int img){ this.imageId=img; }

    public String getName(){ return name; }
    public void setName(String name){ this.name=name; }

    public String getDetail(){ return detail; }
    public void setDetail(String Detail){ this.detail=detail; }

    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price=price; }

    public int getExcellence(){ return excellence; }
    public void setExcellence(int excellence){ this.excellence=excellence; }
}
