package com.bootTest.domain;

/**
 * @Description: 状态码枚举类
 * 
 */

public enum ResCode {
	SUCCESS(0, "OK"),
    WARN(-1, "FAIL");
	
	private int code;
    private String msg;
    
    ResCode(int code, String msg) {
        this.msg = msg;
    }
    
    public int getCode() {
        return code;
    }
    
    public String getMsg() {
        return msg;
    }
}
