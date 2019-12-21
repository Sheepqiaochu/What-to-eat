package com.bootTest.domain;

public class DishFeature {
	private int did;
	private double f1;			//用户特征值f1-f5 辣度
	private double f2;          //甜度
	private double f3;          //酸度
	private double f4;          //咸度
	private double f5;          //油度
	
	public int getDid() {
        return did;
    }

    public void setDid(int uid) {
        this.did = uid;
    }
	
	public void setF1(double f1){
    	this.f1=f1;
    }
    
    public double getF1(){
    	return f1;
    }
    
    public void setF2(double f2){
    	this.f2=f2;
    }
    
    public double getF2(){
    	return f2;
    }
    
    public void setF3(double f3){
    	this.f3=f3;
    }
    
    public double getF3(){
    	return f3;
    }
    
    public void setF4(double f4){
    	this.f4=f4;
    }
    
    public double getF4(){
    	return f4;
    }
    
    public void setF5(double f5){
    	this.f5=f5;
    }
    
    public double getF5(){
    	return f5;
    }
    
    public double getf(int f) {	//获取特征值ֵ
    	switch(f) {
    	case 1:return f1;
    	case 2:return f2;
    	case 3:return f3;
    	case 4:return f4;
    	case 5:return f5;
    	default:return 0;
    	}
    }
    
    public void setf(double i1,double i2,double i3,double i4,double i5) {	//设置特征值
    	this.f1 = i1;
    	this.f2 = i2;
    	this.f3 = i3;
    	this.f4 = i4;
    	this.f5 = i5;
    }
}
