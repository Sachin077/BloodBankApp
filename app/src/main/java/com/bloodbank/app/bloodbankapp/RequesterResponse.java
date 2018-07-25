package com.bloodbank.app.bloodbankapp;

public class RequesterResponse {
    public boolean status;
    public int request_id;
    public RequesterResponse(boolean status, int requestId){
        this.status = status;
        this.request_id = requestId;
    }
}
