package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {
    private EditText email,password;
    private TextView error;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        error = (TextView) findViewById(R.id.errorLogin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().length()>0  && password.getText().toString().length()>0){
                    checkAuthentication(email.getText().toString(),password.getText().toString());

                }

            }
        });

        //makeServiceCall("http://google.com ","");



    }


    private void checkAuthentication(final String email, String password){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final SigninRequest request = new SigninRequest(email,password);
        Call<SigninResponse> call = apiService.login(request);
        call.enqueue(new Callback<SigninResponse>() {
                         @Override
                         public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                             Log.d("response","Getting response from server : "+response);
                             if(response.body().status){
                                 Intent i = new Intent(SigninActivity.this,RoleActivity.class);
                                 i.putExtra("email_id",email);
                                 startActivity(i);
                                 error.setVisibility(View.GONE);
                             }
                             else{
                                 error.setVisibility(View.VISIBLE);
                             }

                         }

                         @Override
                         public void onFailure(Call<SigninResponse> call, Throwable t) {
                             Log.d("response","Getting response from server : "+t);
                             error.setVisibility(View.VISIBLE);
                         }
                     }
        );

    }


}
