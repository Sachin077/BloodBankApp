package com.bloodbank.app.bloodbankapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder>{
    private List<Status> statusList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView  empName, location;

        public MyViewHolder(View view) {
            super(view);
            empName = (TextView) view.findViewById(R.id.status_empName);
            location = (TextView) view.findViewById(R.id.status_location);

        }

    }


    public StatusAdapter(List<Status> statusList) {
        this.statusList = statusList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.status_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Status status = statusList.get(position);
        holder.empName.setText(status.getEmpName());
        holder.location.setText(status.getlocation());

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
