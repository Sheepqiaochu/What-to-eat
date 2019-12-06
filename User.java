package WTE;

import java.util.*;

public class User {		//�û�-��������X
private String UID;		//�û�ID
private double f1;			//����ֵf1-f5
private double f2;
private double f3;
private double f4;
private double f5;

public User(String id,double i1,double i2,double i3,double i4,double i5) {			//���캯��
	this.UID = id;
	this.f1 = i1;
	this.f2 = i2;
	this.f3 = i3;
	this.f4 = i4;
	this.f5 = i5;
}

public void show() {			//��ʾ����X
	System.out.println(this.UID + "  " + this.f1 + "  " + this.f2 + "  " + this.f3 + "  " + this.f4 + "  " + this.f5);
}

public String getid() {			//��ȡID
	return UID;
}

public double getf(int f) {		//��ȡ����ֵ
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

public void recommend(Dish c[],Dish result[]) {		//��������ֵƽ����֮�͵��Ƽ��㷨
	
	Double point[] = new Double[c.length];
	
	for(int k=0;k<c.length;k++) {
		point[k] = (f1 - c[k].getf(1))*(f1 - c[k].getf(1)) + (f2 - c[k].getf(2))*(f2 - c[k].getf(2)) + (f3 - c[k].getf(3))*(f3 - c[k].getf(3)) + (f4 - c[k].getf(4))*(f4 - c[k].getf(4)) + (f5 - c[k].getf(5))*(f5 - c[k].getf(5));
	}
	
	List<suit> list =new ArrayList<>();
	for(int i=0;i<c.length;i++) {
		list.add(new suit(c[i],point[i]));
//		System.out.println(point[i]);
	}

	Collections.sort(list);
	for(int i=0;i<result.length;i++) {
		result[i] = list.get(i).getid();
	}
}
}

class suit implements Comparable<suit>{			//��ƷID������ֵ���ֵĽṹ��
	private Dish id;
	private Double point;
	
	public suit(Dish id,double po) {		//���캯��
		this.id = id;
		this.point = po;
	}
	public Double getpoint() {			//��ȡ����ֵ����
	    return point;
	}
	public Dish getid() {				//��ȡID
		return id;
	}

    public int compareTo(suit o) {		//��д�Ƚ��㷨������sort����
        return this.point.compareTo(o.getpoint());
    }

}
