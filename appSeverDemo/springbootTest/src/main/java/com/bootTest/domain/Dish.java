package com.bootTest.domain;

/**
 * @Description: 菜品实体类 
 * 
 */

public class Dish {
	private String did;        //菜品id
	private String imagePath;  //图片存储路径
	private int rid;           //菜品所属商家id
	private double f1;		   //菜品特征值f1-f5
	private double f2;
	private double f3;
	private double f4;
	private double f5;
	
	public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
    
    public Dish(){
    	
    }
    
    public Dish(String id,double i1,double i2,double i3,double i4,double i5) {		//锟斤拷锟届函锟斤拷
    	this.did = id;
    	this.f1 = i1;
    	this.f2 = i2;
    	this.f3 = i3;
    	this.f4 = i4;
    	this.f5 = i5;
    }

    public void show() {		//锟斤拷示锟斤拷锟斤拷Y
    	System.out.println(this.did + "  " + this.f1 + "  " + this.f2 + "  " + this.f3 + "  " + this.f4 +"  " + this.f5);
    }


    public double getf(int f) {	//锟斤拷取锟斤拷锟斤拷值
    	switch(f) {
    	case 1:return f1;
    	case 2:return f2;
    	case 3:return f3;
    	case 4:return f4;
    	case 5:return f5;
    	default:return 0;
    	}
    }

    public void setf(double i1,double i2,double i3,double i4,double i5) {
    	this.f1 = i1;
    	this.f2 = i2;
    	this.f3 = i3;
    	this.f4 = i4;
    	this.f5 = i5;
    }
}
