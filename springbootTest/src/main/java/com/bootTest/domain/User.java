package com.bootTest.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @Description: 用户实体类
 * 
 */

public class User {
	private int uid;
	private String phoneNumber;
	private String password;
	private String name;
	private double f1;			//用户特征值f1-f5 辣度
	private double f2;          //甜度
	private double f3;          //酸度
	private double f4;          //咸度
	private double f5;          //油度
	
	public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /*public void setF1(double f1){
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
    }*/
    
    public User(){
    	
    }
    
    /*public User(int id,double i1,double i2,double i3,double i4,double i5) {			//锟斤拷锟届函锟斤拷
    	this.uid = id;
    	this.f1 = i1;
    	this.f2 = i2;
    	this.f3 = i3;
    	this.f4 = i4;
    	this.f5 = i5;
    }

    public void show() {			//锟斤拷示锟斤拷锟斤拷X
    	System.out.println(this.uid + "  " + this.f1 + "  " + this.f2 + "  " + this.f3 + "  " + this.f4 + "  " + this.f5);
    }


    public double getf(int f) {		//锟斤拷取锟斤拷锟斤拷值
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

    public void recommend(Dish c[],Dish result[]) {		//锟斤拷锟斤拷锟斤拷锟斤拷值平锟斤拷锟斤拷之锟酵碉拷锟狡硷拷锟姐法
    	
    	Double point[] = new Double[c.length];
    	
    	for(int k=0;k<c.length;k++) {
    		point[k] = (f1 - c[k].getf(1))*(f1 - c[k].getf(1)) + (f2 - c[k].getf(2))*(f2 - c[k].getf(2)) + (f3 - c[k].getf(3))*(f3 - c[k].getf(3)) + (f4 - c[k].getf(4))*(f4 - c[k].getf(4)) + (f5 - c[k].getf(5))*(f5 - c[k].getf(5));
    	}
    	
    	List<suit> list =new ArrayList<>();
    	for(int i=0;i<c.length;i++) {
    		list.add(new suit(c[i],point[i]));
//    		System.out.println(point[i]);
    	}

    	Collections.sort(list);
    	for(int i=0;i<result.length;i++) {
    		result[i] = list.get(i).getid();
    	}
    }*/

}


