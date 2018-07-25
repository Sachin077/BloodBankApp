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
    public List<DonorsInfoResponse> statusList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView  email,name,phone,cab;

        public MyViewHolder(View view) {
            super(view);
            email = (TextView) view.findViewById(R.id.status_emailId);
            name = (TextView) view.findViewById(R.id.status_empName);
            phone = (TextView) view.findViewById(R.id.status_phone);
            cab = (TextView) view.findViewById(R.id.status_cab);
        }

    }


    public StatusAdapter(List<DonorsInfoResponse> statusList) {
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
        DonorsInfoResponse status = statusList.get(position);
        holder.email.setText(status.email_id);
        holder.name.setText(status.name);
        holder.phone.setText(status.phone);
        if(status.cab_needed){
            holder.cab.setText("Needed");
        }
        else{
            holder.cab.setText("Not Needed");
        }

    }

    @Override
    public int getItemCount() {
        return statusList.size();
    }
}
