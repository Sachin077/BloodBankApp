package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonorActivity extends AppCompatActivity {

    private List<CreatedRequestResponse> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter mAdapter;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private int positionInArray;
    private boolean responseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabProfile);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AccountActivity.class);
                startActivity(i);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RequestAdapter(requestList, new ClickListener(){
            @Override
            public void onPositionClicked(int position, int btnType) {
                Log.d("returned to adapter", "onPositionClicked: "+position + " btn type is "+btnType);
                CreateResponseRequest request = new CreateResponseRequest();
                request.email_id = getIntent().getExtras().getString("email_id");
                positionInArray = position;

                //request.email_id = "yo1@yolo.com";
                if(btnType == Constants.YES){
                    request.user_response = true;
                }
                else {
                    request.user_response = false;
                }
                responseType = request.user_response;
                request.request_id = requestList.get(position).id;
                createResponse(request);
            }
        },Constants.DONOR);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        getRequest(getIntent().getExtras().getString("email_id"));
        //getRequest("yo1@yolo.com");
    }

    private void getRequest(String email_id){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<ArrayList<CreatedRequestResponse>> call = apiService.queryRequestForDonor(email_id);
            call.enqueue(new Callback<ArrayList<CreatedRequestResponse>>() {
                             @Override
                             public void onResponse(Call<ArrayList<CreatedRequestResponse>> call, Response<ArrayList<CreatedRequestResponse>> response) {
                                 Log.d("response", "Getting response from server : " + response);
                                 if (response.body() != null) {
                                     requestList = response.body();
                                     mAdapter.requestList = requestList;
                                     mAdapter.notifyDataSetChanged();
                                 } else {

                                 }

                             }

                             @Override
                             public void onFailure(Call<ArrayList<CreatedRequestResponse>> call, Throwable t) {
                                 Log.d("response", "Getting response from server : " + t);

                             }
                         }
            );
        }
        catch (Exception ex) {
            Log.d("Exception in reading: " , ex.getStackTrace().toString());
        }
    }

    private void createResponse(final CreateResponseRequest request){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<SigninResponse> call = apiService.createResponse(request);
        call.enqueue(new Callback<SigninResponse>() {
                         @Override
                         public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                             Log.d("response","Getting response from server : "+response);
                             if(response.body().status){
                                if(responseType == true){
                                    requestList.get(positionInArray).user_response = true;
                                }
                                else{
                                    requestList.remove(positionInArray);
                                }
                                mAdapter.requestList = requestList;
                                mAdapter.notifyDataSetChanged();

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
