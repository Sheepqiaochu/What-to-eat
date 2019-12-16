package WTE;

import java.util.*;

public class Evaluate {			//用户评价类
private int UID;				//用户ID
private Double point[];			//输入物品评分
private Double points[];		//所有物品评分
private int did[];				//物品ID

public int getid() {			//获取用户id
	return UID;
}

public Double getpoint(int j) {		//获取物品评分
	return points[j];
}

public Evaluate(User p,Double po[],int pdid[]) {		//构造函数
	this.UID = p.getid();
	point = new Double[po.length];
	for(int i=0;i<po.length;i++)
		point[i] = po[i];
	did = new int[pdid.length];
	for(int i=0;i<pdid.length;i++)
		did[i] = pdid[i];
}

public void updatepoints(List<Dish> c) {		//生成所有物品的评分数组
	points = new Double[c.size()];
	//points = null;
	for(int i=0;i<did.length;i++)
		points[did[i]-1] = point[i];
}
}
