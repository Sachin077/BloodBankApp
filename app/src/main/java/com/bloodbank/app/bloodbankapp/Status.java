package com.bloodbank.app.bloodbankapp;

public class Status {
    private String empName,location;

    public Status(String empName, String location){
        this.empName = empName;
        this.location = location;
    }

    public String getEmpName(){
        return this.empName;
    }
    public String getlocation(){
        return this.location;
    }
    public void setEmpName(String empName){
        this.empName = empName;
    }
    public void setLocation(String location){
        this.location = location;
    }
}
