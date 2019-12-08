package com.bootTest.domain;

/**
 * @Description: 响应报文格式
 * 
 */

public class ResJson {
	private int code;     //状态码
    private String msg;   //描述
    private Object data;  //数据
    
    public ResJson(ResCode resultCode, Object data) {
        this(resultCode);
        this.data = data;
    }
    
    public ResJson(ResCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }
    
    public void setData(Object data){
    	this.data=data;
    }
    
    public Object getData(){
    	return data;
    }
    
    public int getCode(){
    	return code;
    }
    
    public void setCode(int code){
    	this.code=code;
    }
    
    public String getMsg(){
    	return msg;
    }
    
    public void setMsg(String msg){
    	this.msg=msg;
    }
}
