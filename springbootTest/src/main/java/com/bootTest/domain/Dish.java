package com.bootTest.domain;

/**
 * @Description: 菜品实体类 
 * 
 */

public class Dish {
	private int did;           //菜品id
	private String imagePath;  //图片存储路径
	//private int rid;         //菜品所属商家id
	private String name;       //菜品名称
	private String rname;      //商家名称
	private String raddress;   //商家地址
	private String price;      //菜品价格
	private boolean favorite=false;
	private int priority=0;
	private double f1;		   //菜品特征值f1-f5
	private double f2;
	private double f3;
	private double f4;
	private double f5;
	
	public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }
    
    public String getRaddress() {
        return raddress;
    }

    public void setRaddress(String raddress) {
        this.raddress = raddress;
    }
    
    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
    
    /*public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }*/
    
    public Dish(){
    	
    }
    
    /*public Dish(int id,double i1,double i2,double i3,double i4,double i5) {		//锟斤拷锟届函锟斤拷
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
    }*/
}
