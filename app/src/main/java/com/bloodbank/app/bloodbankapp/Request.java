package com.bloodbank.app.bloodbankapp;

public class Request {
    private String bloodGroup;
    private String empName;
    private int requestId;
    private String location;
    private boolean isCabService;
    private int status;

    public Request(){

    }

    public Request(int requestId, String empName, String bloodGroup, String location, boolean isCabService, int status){
        this.requestId = requestId;
        this.empName = empName;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.isCabService = isCabService;
        this.status = status;
    }

    public int getRequestId(){
        return this.requestId;
    }
    public String getBloodGroup(){
        return this.bloodGroup;
    }

    public String getEmpName(){
        return this.empName;
    }

    public String getLocation(){
        return this.location;
    }

    public boolean getIsCabService(){
        return this.isCabService;
    }

    public void setRequestId(int requestId){
        this.requestId = requestId;
    }
    public void setEmpName(String empName){
        this.empName = empName;
    }
    public void setBloodGroup(String bloodGroup){
        this.bloodGroup = bloodGroup;
    }

    public void setLocation(String location){
        this.location = location;
    }
    public void setCabService(boolean isCabService){
        this.isCabService = isCabService;
    }

    public int getStatus(){
        return this.status;
    }
    public void setStatus(int status){
        this.status = status;
    }
}
