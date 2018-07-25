package com.bloodbank.app.bloodbankapp;

import com.google.gson.annotations.SerializedName;

public class SigninRequest {
    @SerializedName("email_id")
    private String email_id;
    @SerializedName("password")
    private String password;

    public SigninRequest(String email_id,String password){
        this.email_id = email_id;
        this.password = password;
    }
}
