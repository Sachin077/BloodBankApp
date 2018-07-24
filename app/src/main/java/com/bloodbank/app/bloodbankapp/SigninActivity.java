package com.bloodbank.app.bloodbankapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SigninActivity extends AppCompatActivity {
    private EditText email,password;
    private TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        error = (TextView) findViewById(R.id.errorLogin);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isAuthenticated =  checkAuthentication(email.getText().toString(),password.getText().toString());
                if(isAuthenticated){

                }
                else{
                    error.setVisibility(View.VISIBLE);
                }
            }
        });


    }
    private boolean checkAuthentication(String email, String password){
        return false;
    }

}