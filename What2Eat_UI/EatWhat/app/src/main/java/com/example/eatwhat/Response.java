package com.example.eatwhat;

public class Response {
    private int code;
    private String data;
    private String msg;

    public int getCode(){ return code; }
    public void setCode(int code){ this.code=code; }

    public String getData(){ return data; }
    public void setData(String data){ this.data=data; }

    public String getMsg(){ return msg; }
    public void setMsg(String data){ this.msg=msg; }
}
