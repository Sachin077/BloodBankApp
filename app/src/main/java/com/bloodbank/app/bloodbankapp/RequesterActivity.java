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
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequesterActivity extends AppCompatActivity {
    private List<CreatedRequestResponse> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter mAdapter;
    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabCreateRequest);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("email_id",getIntent().getExtras().getString("email_id"));
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
                Toast.makeText(getApplicationContext(), "btn type "+btnType+" with data  ", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(getApplicationContext(),RequesterStatusActivity.class);
                i.putExtra("request_id",requestList.get(position).id);
                startActivity(i);
            }
        },Constants.REQUESTER);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        getRequest(getIntent().getExtras().getString("email_id"));
        //getRequest("yo1@yolo.com");
        //bindValue();
        //mAdapter.notifyDataSetChanged();
    }

    private void bindValue(){
        CreatedRequestResponse req1= new CreatedRequestResponse();
        req1.location="1";
        requestList.add(req1);
    }


    private void getRequest(String email_id){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        try {
            Call<ArrayList<CreatedRequestResponse>> call = apiService.getRequestByUser(email_id);
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

}
