package com.bootTest.domain;

import java.util.List;

/**
 * @Description: 用户评价类 
 */

public class Evaluate {
private int uid;
private Double point[];         //输入物品评分
private Double points[];		//所有物品评分
private int did[];              //输入物品id

public int getUid() {
	return uid;
}

public void setUid(int uid){
	this.uid=uid;
}

public Evaluate(){
	
}

public Double[] getPoint(){
	return point;
}

public void setPoint(Double point[]){
	this.point = new Double[point.length];
	for(int i=0;i<point.length;i++)
		this.point[i] = point[i];
}

public int[] getDid(){
	return did;
}

public void setDid(int did[]){
	this.did = new int[did.length];
	for(int i=0;i<did.length;i++)
		this.did[i] = did[i];
}

public Double getPoints(int j) {
	return points[j];
}

/*public Evaluate(User p,Double po[]) {
	this.uid = p.getUid();
	point = new Double[po.length];
	for(int i=0;i<po.length;i++)
		point[i] = po[i];
}*/

public void updatePoints(int dishNumber) {		//生成所有物品的评分数组
	points = new Double[dishNumber];
	//points = null;
	for(int i=0;i<did.length;i++)
		points[did[i]-1] = point[i];
}
}
