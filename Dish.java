package WTE;

public class Dish {			//物品-特征矩阵Y
private String DID;			//物品ID
private double f1;				//特征值f1-f5
private double f2;
private double f3;
private double f4;
private double f5;

public Dish(String id,double i1,double i2,double i3,double i4,double i5) {		//构造函数
	this.DID = id;
	this.f1 = i1;
	this.f2 = i2;
	this.f3 = i3;
	this.f4 = i4;
	this.f5 = i5;
}

public void show() {		//显示矩阵Y
	System.out.println(this.DID + "  " + this.f1 + "  " + this.f2 + "  " + this.f3 + "  " + this.f4 +"  " + this.f5);
}

public String getid() {		//获取ID
	return DID;
}

public double getf(int f) {	//获取特征值
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
