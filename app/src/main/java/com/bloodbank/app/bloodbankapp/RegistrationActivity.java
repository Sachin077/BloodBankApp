package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegistration;
    private EditText reg_name,reg_location,reg_bloodGroup;
    private Spinner bloodGroup;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnRegistration = (Button) findViewById(R.id.btnRegister);
        btnRegistration.setOnClickListener(this);
        reg_name = (EditText) findViewById(R.id.reg_fullname);
        reg_location = (EditText) findViewById(R.id.reg_location);
        //reg_bloodGroup = (EditText) findViewById(R.id.reg_bloodGroup);
        bloodGroup = (Spinner) findViewById(R.id.spinner_bg);
        List<String> bg_list = new ArrayList<String>();
        bg_list.add("A+");
        bg_list.add("A");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bg_list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(getApplicationContext(), DonorActivity.class);
        startActivity(i);
    }
}
