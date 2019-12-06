package WTE;

import java.text.DecimalFormat;

import java.util.*;
 
class Matrix{    //矩阵类
 
    private int row;//行 
    private int col;//列 
    double [][]Data;
  
    public Matrix(int row, int col,double [][]Data) {		//构造函数
        this.row = row;
        this.col = col; 
        this.Data = Data;
    }
 
    public void setMatrix(int row , int col, double value) {	//设定矩阵中某一位置的值
        this.Data[row - 1][col - 1] = value;
    }
 
    public double getMatrix(int row, int col) {		//获取矩阵中某一位置
        return Data[row - 1][col - 1] ;
    }
 
    public int width() {		//获取行数
        return row;
    }
 
    public int height() {		//获取列数
        return col;
    }
 
    public Matrix add(Matrix b) {		//矩阵加法
 
        if(this.width() != b.width() && this.height() != b.height()) {
            return null;
        }
 
        double add[][] = new double[this.row][this.col];
 
        for(int i = 0;i<col;i++) {
            for(int j = 0;j<row;j++) {
                add[i][j] = this.Data[i][j] + b.Data[i][j];
            }
        }
 
        Matrix another = new Matrix(this.col,this.row,add);    
 
        System.out.println("after add:");
 
        return another;
 
    }
 
    public Matrix multiply(Matrix b) {		//矩阵乘法
 
        if(this.col != b.row) {
            return null;
        }
 
        double mul[][] = new double[this.row][b.col];
 
        double temp = 0;
 
        for(int i = 0;i<this.row;i++) {
            for(int k = 0;k<b.col;k++) {
                for(int j = 0;j<this.col;j++){
                    temp += this.Data[i][j] * b.Data[j][k];
                    }
 
                mul[i][k] = temp;
 
                temp = 0;
 
            }
 
        }
 
        Matrix another = new Matrix(this.row, b.col, mul);
 
        System.out.println("after multiply:");
 
        return another;
 
    }
 
    public Matrix transpose() {			//矩阵转置
 
        double tran[][] = new double[this.col][this.row];
 
        for(int i = 0;i<this.row;i++) {
            for(int j = 0;j<this.col;j++) {
                tran[j][i] = this.Data[i][j];
            }
        }
 
        Matrix another = new Matrix(this.col,this.row,tran);
 
//      System.out.println("after transpose:");
 
        return another;
    }
 
    public void show() {		//显示矩阵
    	for(int i=0;i<row;i++) {
    		for(int j=0;j<col;j++)
    			System.out.print(Data[i][j]+"  ");
    		System.out.println();
    	}
    		
    }
    
    public Matrix reverse() {		//矩阵求逆
        ReverseMartrix rm = new ReverseMartrix();
        
    	Matrix newMatrix = new Matrix(row,col,rm.getReverseMartrix(Data));
    	
    	return newMatrix;
    }
}
