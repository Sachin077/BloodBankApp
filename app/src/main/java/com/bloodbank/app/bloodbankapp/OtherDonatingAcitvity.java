package com.bloodbank.app.bloodbankapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtherDonatingAcitvity extends AppCompatActivity {
    private String email_id;
    private int request_id;
    private ActionBar actionBar;
    private RecyclerView recyclerView;
    private StatusAdapter statusAdapter;
    private TextView empty_view;
    private List<DonorsInfoResponse> statusList = new ArrayList<DonorsInfoResponse>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_donating_acitvity);
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

        SharedPreferences sharedPreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        email_id=sharedPreferences.getString("email_id",email_id);
        request_id = sharedPreferences.getInt("request_id",0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_status);
        Log.d("recyclerView", "onCreate: "+ recyclerView);
        statusAdapter = new StatusAdapter(statusList);
        recyclerView.setAdapter(statusAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setHasFixedSize(true);

        empty_view = (TextView) findViewById(R.id.empty_view);

        getRequest(request_id);
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs
        Intent i = new Intent(this, DonorActivity.class);
        startActivity(i);
    }

    private void getRequest(int request_id){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<ArrayList<DonorsInfoResponse>> call = apiService.getResponsesByRequest(request_id);
            call.enqueue(new Callback<ArrayList<DonorsInfoResponse>>() {
                             @Override
                             public void onResponse(Call<ArrayList<DonorsInfoResponse>> call, Response<ArrayList<DonorsInfoResponse>> response) {
                                 Log.d("response", "Getting response from server : " + response);
                                 if (response.body() != null) {
                                     statusList = response.body();
                                     List<DonorsInfoResponse> dupstatusList = new ArrayList<DonorsInfoResponse>();
                                     dupstatusList = statusList;
                                     for(int i=0;i<dupstatusList.size();i++){
                                         if(dupstatusList.get(i).email_id.equals(email_id)){
                                             statusList.remove(i);
                                         }
                                     }
                                     if(statusList.size() == 0){
                                         empty_view.setText("Currently no other donors found");
                                         recyclerView.setVisibility(View.GONE);
                                     }
                                     else{
                                         empty_view.setVisibility(View.GONE);
                                         recyclerView.setVisibility(View.VISIBLE);
                                     }
                                     statusAdapter.statusList = statusList;
                                     statusAdapter.notifyDataSetChanged();
                                 } else {

                                 }

                             }

                             @Override
                             public void onFailure(Call<ArrayList<DonorsInfoResponse>> call, Throwable t) {
                                 Log.d("response", "Getting response from server : " + t);

                             }
                         }
            );
        }
        catch (Exception ex) {
            Log.d("Exception in reading: " , ex.getStackTrace().toString());
        }
    }

}
