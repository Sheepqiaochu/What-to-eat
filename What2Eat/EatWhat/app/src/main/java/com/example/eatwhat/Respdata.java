package com.example.eatwhat;

public class Respdata {
    private int code;
    private class data{
        String name;
        String password;
        String phoneNumber;
        int uid;
    };
    private String msg;

    public int getCode(){ return code; }
    public void setCode(int code){ this.code=code; }

    //public data getData(){ return data; }
    //public void setData(data d){ this.data=data; }

    public String getMsg(){ return msg; }
    public void setMsg(String data){ this.msg=msg; }
}
