package com.example.eatwhat;

import android.graphics.Bitmap;

import java.io.Serializable;

//推荐的食物
public class RecommendFood implements Serializable {
    private int did;        //食物id
    private int imageId;    //图片源
    private String name;    //名称
    private String detail;  //详细介绍
    private double price;      //价格
    private int excellence; //推荐度,满分10
    private double f1;      //辣度
    private double f2;      //甜度
    private double f3;      //酸度
    private double f4;      //咸度
    private double f5;      //油度
    private String imageUrl;    //食物图片url
    private boolean favorite;  //是否收藏


    public RecommendFood(){

    }
    public RecommendFood(String name,String detail, int imageId,double price,int excellence,double f1,double f2,double f3,double f4,double f5){
        this.name = name;
        this.detail=detail;
        this.imageId = imageId;
        this.price=price;
        this.excellence=excellence;
        this.f1=f1;
        this.f2=f2;
        this.f3=f3;
        this.f4=f4;
        this.f5=f5;
    }

    public int getDid(){ return did; }
    public void setDid(int did){ this.did=did; }

    public int getImageId(){ return imageId; }
    public void setImageId(int img){ this.imageId=img; }

    public String getName(){ return name; }
    public void setName(String name){ this.name=name; }

    public String getDetail(){ return detail; }
    public void setDetail(String detail){ this.detail=detail; }

    public double getPrice(){ return price; }
    public void setPrice(double price){ this.price=price; }

    public int getExcellence(){ return excellence; }
    public void setExcellence(int excellence){ this.excellence=excellence; }

    public double getF1(){ return f1; }
    public void setF1(double f1){ this.f1=f1; }

    public double getF2(){ return f2; }
    public void setF2(double f2){ this.f2=f2; }

    public double getF3(){ return f3; }
    public void setF3(double f3){ this.f3=f3; }

    public double getF4(){ return f4; }
    public void setF4(double f4){ this.f4=f4; }

    public double getF5(){ return f5; }
    public void setF5(double f5){ this.f5=f5; }

    public String getImageUrl(){ return imageUrl; }
    public void setImageUrl(String imageUrl){ this.imageUrl=imageUrl; }

    public boolean getFavorite(){ return favorite; }
    public void setFavorite(boolean favorite){ this.favorite=favorite; }
}
