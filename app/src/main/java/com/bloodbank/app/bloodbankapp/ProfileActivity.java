package com.bloodbank.app.bloodbankapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private DatabaseReference mDatabase;
    private Spinner bloodGroupSpinner;
    private EditText addressET, deadlineET, unitsET, storyET;
    private Switch provideCabSwitch;

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
    }

    private void writeNewPost() {
        String key = mDatabase.child("requests").push().getKey();
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", addressET.getText().toString());
        result.put("deadline", deadlineET.getText().toString());
        result.put("units", unitsET.getText().toString());
        result.put("story", storyET.getText().toString());
        result.put("bloodGroup", bloodGroupSpinner.getSelectedItem().toString());
        result.put("provideCab", provideCabSwitch.isChecked());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/requests/" + key, result);
        mDatabase.updateChildren(childUpdates);
        String some = mDatabase.child("requests").child("1").toString();
        Toast.makeText(getApplicationContext(), some, Toast.LENGTH_SHORT).show();
    }

}
