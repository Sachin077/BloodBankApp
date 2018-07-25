package com.bloodbank.app.bloodbankapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("login/")
    Call<SigninResponse> login(@Body SigninRequest request);

    @POST("register/")
    Call<SigninResponse> login(@Body SignupRequest request);

    @POST("createRequest/")
    Call<RequesterResponse> login(@Body RequesterRequest request);

    @GET("getRequestByUser/")
    Call<ArrayList<CreatedRequestResponse>> getRequestByUser(@Query("email_id") String email_id);
}
