package com.bootTest.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserFeature {
	private int uid;
	private double f1;			//用户特征值f1-f5 辣度
	private double f2;          //甜度
	private double f3;          //酸度
	private double f4;          //咸度
	private double f5;          //油度
	private List<suit> list=new ArrayList<>();
	
	public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
    
    public double getf(int f) {		//获取特征值ֵ
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

    public void d_sort(List<DishFeature> c) {		//按照特征值差平方对物品数组排序
	
    	Double point[] = new Double[c.size()];
	
    	for(int k=0;k<c.size();k++) {
		point[k] = (f1 - c.get(k).getf(1))*(f1 - c.get(k).getf(1)) + (f2 - c.get(k).getf(2))*(f2 - c.get(k).getf(2)) + (f3 - c.get(k).getf(3))*(f3 - c.get(k).getf(3)) + (f4 - c.get(k).getf(4))*(f4 - c.get(k).getf(4)) + (f5 - c.get(k).getf(5))*(f5 - c.get(k).getf(5));
    	}
	
	
    	for(int i=0;i<c.size();i++) {
    		list.add(new suit(c.get(i),point[i]));
    		//		System.out.println(point[i]);
    	}

    	Collections.sort(list);
	
    }

    public int[] recommend(int k,int num) {		//从第k个开始推荐物品,推荐num个菜
    	int result[] = new int[num];
    	//List<Integer> result=new ArrayList<>();
    	for(int i=0;i<num;i++) {
    		result[i] = list.get(k+i).getid().getDid();
    		//result.add(list.get(k+i).getid().getDid());
    		//k++;
    	}
    	return result;
    }

}

class suit implements Comparable<suit>{			//用于排序的结构体
	private DishFeature id;
	private Double point;
	
	public suit(DishFeature id,double po) {		//构造函数
		this.id = id;
		this.point = po;
	}
	public Double getpoint() {			//获取评分
	    return point;
	}
	public DishFeature getid() {				//获取ID
		return id;
	}

    public int compareTo(suit o) {		//重写比较以使用sort函数
        return this.point.compareTo(o.getpoint());
    }

}
