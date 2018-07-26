package com.bloodbank.app.bloodbankapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegistration;
    private EditText fullNameReg,empIdReg,emailReg,phoneReg,addressReg, passwordReg;
    private Spinner bloodGroupReg,cityReg;
    private RadioGroup genderReg;
    private Switch fitToDonateReg;
    private String token;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
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

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast
                        Log.d("MyActivity", token);
                    }
                });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton btn = (RadioButton) findViewById(genderReg.getCheckedRadioButtonId());
                SignupRequest request = new SignupRequest(Long.parseLong(empIdReg.getText().toString()),emailReg.getText().toString(), fullNameReg.getText().toString(),Long.parseLong(phoneReg.getText().toString()),btn.getText().toString(),bloodGroupReg.getSelectedItem().toString(),addressReg.getText().toString(),cityReg.getSelectedItem().toString() ,fitToDonateReg.isChecked(), passwordReg.getText().toString(), token);
                register(request);
            }
        });

        fullNameReg = (EditText) findViewById(R.id.fullNameReg);
        empIdReg = (EditText) findViewById(R.id.EmpIdReg);
        emailReg = (EditText) findViewById(R.id.emailReg);
        passwordReg = (EditText) findViewById(R.id.passwordReg);
        phoneReg = (EditText) findViewById(R.id.phoneReg);
        bloodGroupReg = (Spinner) findViewById(R.id.bloodGrpReg);
        genderReg = (RadioGroup) findViewById(R.id.genderGroupReg);
        fitToDonateReg = (Switch) findViewById(R.id.donatableReg);
        addressReg = (EditText) findViewById(R.id.addressReg);
        cityReg = (Spinner) findViewById(R.id.cityReg);

    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
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
                                 SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                                 sharedpreferences.edit().putString("email_id",request.email_id).commit();
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
