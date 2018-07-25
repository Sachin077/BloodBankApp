package com.bloodbank.app.bloodbankapp;

public class SigninResponse {
    public boolean status;
    public String msg;
    public SigninResponse(boolean status, String msg){
        this.status = status;
        this.msg = msg;
    }
}
