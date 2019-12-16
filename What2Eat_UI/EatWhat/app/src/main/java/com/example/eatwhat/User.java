package com.example.eatwhat;

import java.io.Serializable;

public class User implements Serializable {
    private int uid;
    private String phoneNumber;
    private String password;
    private String name;
    private double f1;
    private double f2;
    private double f3;
    private double f4;
    private double f5;

    public User(){

    }
    public User(String phoneNumber,String password){
        this.phoneNumber=phoneNumber;
        this.password=password;
    }

    public String getName(){ return name; }
    public void setName(String name){ this.name=name; }

    public String getPhoneNumber(){ return phoneNumber; }
    public void setPhoneNumber(String phoneNumber){ this.phoneNumber=phoneNumber; }

    public String getPassword(){ return password; }
    public void setPassword(String password){ this.password=password; }

    public int getUid(){ return uid; }
    public void setUid(int uid){ this.uid=uid; }

    public double getF1(){ return f1; }
    public void setF1(double f1){ this.f1=f1; }

    public double getF2(){ return f2; }
    public void setF2(double f2){ this.f2=f2; }

    public double getF3(){ return f3; }
    public void setF3(double f3){ this.f3=f3; }

    public double getF4(){ return f4; }
    public void setF4(double f4){ this.f4=f4; }

    public double getF5(){ return f5; }
    public void setF5(double f5){ this.f5=f5; }
}
