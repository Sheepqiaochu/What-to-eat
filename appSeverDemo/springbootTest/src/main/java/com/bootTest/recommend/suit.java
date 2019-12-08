package com.bootTest.recommend;

import com.bootTest.domain.Dish;

public class suit implements Comparable<suit>{
	private Dish id;
	private Double point;
	
	public suit(Dish id,double po) {		//锟斤拷锟届函锟斤拷
		this.id = id;
		this.point = po;
	}
	public Double getpoint() {			//锟斤拷取锟斤拷锟斤拷值锟斤拷锟斤拷
	    return point;
	}
	public Dish getid() {				//锟斤拷取ID
		return id;
	}

    public int compareTo(suit o) {		//锟斤拷写锟饺斤拷锟姐法锟斤拷锟斤拷锟斤拷sort锟斤拷锟斤拷
        return this.point.compareTo(o.getpoint());
    }
}
