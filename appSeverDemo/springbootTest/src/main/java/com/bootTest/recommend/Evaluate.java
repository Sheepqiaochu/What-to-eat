package com.bootTest.recommend;

import com.bootTest.domain.User;

public class Evaluate {
private String uid;
private Double point[];

public String getid() {
	return uid;
}

public Double getpoint(int j) {
	return point[j];
}

public Evaluate(User p,Double po[]) {
	this.uid = p.getUid();
	point = new Double[po.length];
	for(int i=0;i<po.length;i++)
		point[i] = po[i];
}
}
