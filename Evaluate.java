package WTE;

public class Evaluate {			//用户评价类
private String UID;				//用户ID
private Double point[];			//评价值数组

public String getid() {			//获取id
	return UID;
}

public Double getpoint(int j) {		//获取某一物品的评价
	return point[j];
}

public Evaluate(User p,Double po[]) {		//构造函数
	this.UID = p.getid();
	point = new Double[po.length];
	for(int i=0;i<po.length;i++)
		point[i] = po[i];
}
}
