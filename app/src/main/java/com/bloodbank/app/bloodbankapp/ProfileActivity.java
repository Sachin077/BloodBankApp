package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DatabaseReference mDatabase;
    private Spinner bloodGroupSpinner,cityET;
    private EditText addressET, deadlineET, unitsET, storyET;
    private Switch provideCabSwitch;
    private String email_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addressET = (EditText) findViewById(R.id.addressET);
        deadlineET = (EditText) findViewById(R.id.deadlineET);
        unitsET = (EditText) findViewById(R.id.unitsET);
        storyET = (EditText) findViewById(R.id.storyET);
        bloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        provideCabSwitch = (Switch) findViewById(R.id.provideCabSwitch);
        cityET = (Spinner) findViewById(R.id.cityET);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeNewPost();
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        email_id = getIntent().getExtras().getString("email_id");
    }

    private void writeNewPost() {
        RequesterRequest request = new RequesterRequest(email_id,bloodGroupSpinner.getSelectedItem().toString(),Integer.parseInt(unitsET.getText().toString()),addressET.getText().toString(),deadlineET.getText().toString(),storyET.getText().toString(),provideCabSwitch.isChecked(),cityET.getSelectedItem().toString());
        register(request);
    }

    private void register(RequesterRequest request){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<RequesterResponse> call = apiService.login(request);
        call.enqueue(new Callback<RequesterResponse>() {
                         @Override
                         public void onResponse(Call<RequesterResponse> call, Response<RequesterResponse> response) {
                             Log.d("response","Getting response from server : "+response);
                             if(response.body().status){
                                 Intent i = new Intent(getApplicationContext(), RequesterActivity.class);
                                 startActivity(i);
                             }
                             else{

                             }

                         }

                         @Override
                         public void onFailure(Call<RequesterResponse> call, Throwable t) {
                             Log.d("response","Getting response from server : "+t);

                         }
                     }
        );

    }

}
