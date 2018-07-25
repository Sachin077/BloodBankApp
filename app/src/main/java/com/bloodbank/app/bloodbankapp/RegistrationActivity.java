package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegistration;
    private EditText fullNameReg,empIdReg,emailReg,phoneReg,addressReg;
    private Spinner bloodGroupReg,cityReg;
    private RadioGroup genderReg;
    private Switch fitToDonateReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton btn = (RadioButton) findViewById(genderReg.getCheckedRadioButtonId());
                SignupRequest request = new SignupRequest(Long.parseLong(empIdReg.getText().toString()),emailReg.getText().toString(),fullNameReg.getText().toString(),Long.parseLong(phoneReg.getText().toString()),btn.getText().toString(),bloodGroupReg.getSelectedItem().toString(),addressReg.getText().toString(),cityReg.getSelectedItem().toString() ,fitToDonateReg.isChecked(),"pass123");
                register(request);
            }
        });

        fullNameReg = (EditText) findViewById(R.id.fullNameReg);
        empIdReg = (EditText) findViewById(R.id.EmpIdReg);
        emailReg = (EditText) findViewById(R.id.emailReg);
        phoneReg = (EditText) findViewById(R.id.phoneReg);
        bloodGroupReg = (Spinner) findViewById(R.id.bloodGrpReg);
        genderReg = (RadioGroup) findViewById(R.id.genderGroupReg);
        fitToDonateReg = (Switch) findViewById(R.id.donatableReg);
        addressReg = (EditText) findViewById(R.id.addressReg);
        cityReg = (Spinner) findViewById(R.id.cityReg);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), DonorActivity.class);
        startActivity(i);
    }

    private void register(final SignupRequest request){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<SigninResponse> call = apiService.login(request);
        call.enqueue(new Callback<SigninResponse>() {
                         @Override
                         public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                             Log.d("response","Getting response from server : "+response);
                             if(response.body().status){
                                 Intent i = new Intent(getApplicationContext(), RoleActivity.class);
                                 i.putExtra("email_id",request.email_id);
                                 startActivity(i);
                             }
                             else{

                             }

                         }

                         @Override
                         public void onFailure(Call<SigninResponse> call, Throwable t) {
                             Log.d("response","Getting response from server : "+t);

                         }
                     }
        );

    }
}
