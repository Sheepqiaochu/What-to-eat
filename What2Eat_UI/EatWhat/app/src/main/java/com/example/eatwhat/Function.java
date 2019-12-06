package com.example.eatwhat;

//作为ListView适配器的适配类型
public class Function {
    private String name;//功能名
    private int imageId;//对应的图标资源的id

    public Function(String name,int imageId) {
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
