package WTE;

import java.util.*;

public class User {		//用户类
private int UID;		//用户ID
private double f1;			//特征值ֵf1-f5
private double f2;
private double f3;
private double f4;
private double f5;
private List<suit> list;

public User(int id,double i1,double i2,double i3,double i4,double i5) {			//构造函数
	this.UID = id;
	this.f1 = i1;
	this.f2 = i2;
	this.f3 = i3;
	this.f4 = i4;
	this.f5 = i5;
	list =new ArrayList<>();
}

public void show() {			//显示信息
	System.out.println(this.UID + "  " + this.f1 + "  " + this.f2 + "  " + this.f3 + "  " + this.f4 + "  " + this.f5);
}

public int getid() {			//获取ID
	return UID;
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

public void d_sort(List<Dish> c) {		//按照特征值差平方对物品数组排序
	
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

public int[] recommend(int k) {		//从第k个开始推荐物品
	int result[] = new int[5];
	for(int i=0;i<5;i++) {
		result[i] = list.get(k+i).getid().getid();
		k++;
	}
	return result;
}

}

class suit implements Comparable<suit>{			//用于排序的结构体
	private Dish id;
	private Double point;
	
	public suit(Dish id,double po) {		//构造函数
		this.id = id;
		this.point = po;
	}
	public Double getpoint() {			//获取评分
	    return point;
	}
	public Dish getid() {				//获取ID
		return id;
	}

    public int compareTo(suit o) {		//重写比较以使用sort函数
        return this.point.compareTo(o.getpoint());
    }

}
