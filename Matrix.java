package WTE;

import java.text.DecimalFormat;

import java.util.*;
 
class Matrix{
 
    private int row;//�� 
    private int col;//�� 
    double [][]Data;
  
    public Matrix(int row, int col,double [][]Data) {		//���캯��
        this.row = row;
        this.col = col; 
        this.Data = Data;
    }
 
    public void setMatrix(int row , int col, double value) {	//�趨������ĳһλ�õ�ֵ
        this.Data[row - 1][col - 1] = value;
    }
 
    public double getMatrix(int row, int col) {		//��ȡ������ĳһλ��
        return Data[row - 1][col - 1] ;
    }
 
    public int width() {		//��ȡ����
        return row;
    }
 
    public int height() {		//��ȡ����
        return col;
    }
 
    public Matrix add(Matrix b) {		//����ӷ�
 
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
 
    public Matrix multiply(Matrix b) {		//����˷�
 
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
 
    public Matrix transpose() {			//����ת��
 
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
 
    public void show() {		//��ʾ����
    	for(int i=0;i<row;i++) {
    		for(int j=0;j<col;j++)
    			System.out.print(Data[i][j]+"  ");
    		System.out.println();
    	}
    		
    }
    
 /*   public String toString() {	//ת��Ϊ�ַ���
 
        DecimalFormat df = new DecimalFormat("0");
 
        String result = "";
 
        //result += df.format(Data[0][0]);
 
        
 
        for(int i = 0;i<this.row;i++) {
 
            result += df.format(Data[i][0]);
 
            for(int j = 1;j<this.col;j++) {
 
                result += " " + df.format(Data[i][j]);
 
            }
 
            result +=  "\n";
 
        }
 
        return result;
 
    
 
    }*/
    
    public Matrix reverse() {		//��������
        ReverseMartrix rm = new ReverseMartrix();
        
    	Matrix newMatrix = new Matrix(row,col,rm.getReverseMartrix(Data));
    	
    	return newMatrix;
    }
}
