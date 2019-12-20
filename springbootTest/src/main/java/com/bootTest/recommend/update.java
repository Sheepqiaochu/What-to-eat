package com.bootTest.recommend;
import java.util.*;

import com.bootTest.domain.DishFeature;
import com.bootTest.domain.Evaluate;
import com.bootTest.domain.UserFeature;

public class update {		//用于算法分析的类
private Matrix A;			//用户-物品评分矩阵
private Matrix X;			//用户特征值矩阵
private Matrix Y;			//物品-特征值矩阵
private int m;				//用户数量（行数）
private int n;				//物品数量（列数）
private Map<Integer,Integer> users = new HashMap<>();	//用户ID与下标的映射

public update(List<UserFeature> p,List<DishFeature> c) {		//构造函数
	m = p.size();
	n = c.size();

	double A1[][] = new double[m][n];
	for(int i=0;i<m;i++) {
		users.put(p.get(i).getUid(), i);		//建立映射
		
		for(int j=0;j<n;j++) {
			A1[i][j] = 0;
			for(int k=1;k<=5;k++)
				A1[i][j] += p.get(i).getf(k)*c.get(j).getf(k);		//A = X * YT
		}
	}
	
	A = new Matrix(m ,n ,A1);
	
	double X1[][] = new double[m][5];
	for(int i=0;i<m;i++)
		for(int j=0;j<5;j++)
			X1[i][j] = p.get(i).getf(j+1);
	X = new Matrix(m ,5 ,X1);
	
	double Y1[][] = new double[n][5];
	for(int i=0;i<n;i++)
		for(int j=0;j<5;j++)
			Y1[i][j] = c.get(i).getf(j+1);
	Y = new Matrix(n ,5 ,Y1);
}

public void show() {		//显示信息
	A.show();
	X.show();
	Y.show();
}

public void update_user(Evaluate o) {		//更新评价矩阵
	int i = users.get(o.getUid());	
	for(int j=0;j<n;j++)
		if(o.getPoints(j) != null)
			A.setMatrix(i+1, j+1, o.getPoints(j));
			
}

public void resolve(List<UserFeature> p,List<DishFeature> c){		//将A分解为X，Y
	
	double e[][] = new double[5][5];
	for(int i=0;i<5;i++)
		for(int j=0;j<5;j++)
			if(i==j)
				e[i][j] = 5;
			else
				e[i][j] = 0;
	Matrix E = new Matrix(5,5,e);
	
	for(int count=0;count<6;count++) {
		X = A.multiply(Y).multiply(Y.transpose().multiply(Y).add(E).reverse());					//X=AY(YTY+E)^-1
	//	X.show();
		Y = A.transpose().multiply(X).multiply(X.transpose().multiply(X).add(E).reverse());		//Y=ATX(XTX+E)^-1
	//	Y.show();
	}
	A = X.multiply(Y.transpose());
	
	for(int i=0;i<m;i++)
		p.get(i).setf(X.getMatrix(i+1, 1),X.getMatrix(i+1, 2),X.getMatrix(i+1, 3),X.getMatrix(i+1, 4),X.getMatrix(i+1, 5));
	/*for(int i=0;i<n;i++)
		c.get(i).setf(Y.getMatrix(i+1, 1),Y.getMatrix(i+1, 2),Y.getMatrix(i+1, 3),Y.getMatrix(i+1, 4),Y.getMatrix(i+1, 5));
	*/
	
}
}