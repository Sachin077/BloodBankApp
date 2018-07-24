package com.bloodbank.app.bloodbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class DonorActivity extends AppCompatActivity {

    private List<Request> requestList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RequestAdapter(requestList, new ClickListener(){
            @Override
            public void onPositionClicked(int position, int btnType) {
                Log.d("returned to adapter", "onPositionClicked: "+position + " btn type is "+btnType);
                Toast.makeText(getApplicationContext(), "btn type "+btnType+" with data  " + requestList.get(position).getEmpName(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), RequesterActivity.class);
                startActivity(i);
            }
        },Constants.DONOR);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        prepareRequestData();
    }

    private void prepareRequestData() {
        Request request1 = new Request(1,"test","a+","Kondapur",true);
        requestList.add(request1);
        Request request2 = new Request(2,"test2","b+","Kothaguda",false);
        requestList.add(request2);
        request2 = new Request(3,"test3","b+","Kothaguda",false);
        requestList.add(request2);
        request2 = new Request(4,"test4","b+","Kothaguda",false);
        requestList.add(request2);
        mAdapter.notifyDataSetChanged();
    }

}
