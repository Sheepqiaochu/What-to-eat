package com.bootTest.domain;

/**
 * @Description: 商家实体类
 * 
 */

public class Restaurant {
	private int rid;               //商家id
	private String name;           //商家名称
	private String adress;         //商家地址
	private String resImagePath;   //商家图片存储路径
	
	public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    public String getResImagePath() {
        return resImagePath;
    }

    public void setResImagePath(String resImagePath) {
        this.resImagePath = resImagePath;
    }

}
