package com.bloodbank.app.bloodbankapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {

    private final ClickListener listener;
    private List<Request> requestList;
    private int type;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView requestId, empName, bloodGroup, location, isCabService;
        public LinearLayout buttoncontainer;
        public Button btnYes,btnNo,btnEdit;
        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(View view, ClickListener listener) {
            super(view);
            listenerRef = new WeakReference<>(listener);
            requestId = (TextView) view.findViewById(R.id.requestId);
            empName = (TextView) view.findViewById(R.id.empName);
            bloodGroup = (TextView) view.findViewById(R.id.bloodGroup);
            location = (TextView) view.findViewById(R.id.location);
            isCabService = (TextView) view.findViewById(R.id.cabService);
            buttoncontainer = (LinearLayout)view.findViewById(R.id.buttonContainer);
            btnEdit = (Button) view.findViewById(R.id.btnEdit);
            btnEdit.setOnClickListener(this);
            btnYes = (Button) view.findViewById(R.id.btnYes);
            btnYes.setOnClickListener(this);
            btnNo = (Button) view.findViewById(R.id.btnNo);
            btnNo.setOnClickListener(this);
            if(type == Constants.REQUESTER){
                buttoncontainer.setVisibility(view.GONE);

            }
            else{
                btnEdit.setVisibility(view.GONE);

            }
        }

        @Override
        public void onClick(View view) {
            Log.d("myTag", "onClick: "+view.getId());
            if(view.getId() == btnYes.getId()){
                Toast.makeText(view.getContext(), "ITEM PRESSED Yes = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                listenerRef.get().onPositionClicked(getAdapterPosition(), Constants.YES);
            }
            else if(view.getId() == btnNo.getId()){
                Toast.makeText(view.getContext(), "ITEM PRESSED No = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                listenerRef.get().onPositionClicked(getAdapterPosition(), Constants.NO);
            }
            else if(view.getId() == btnEdit.getId()){
                listenerRef.get().onPositionClicked(getAdapterPosition(), Constants.EDIT);
            }

        }
    }


    public RequestAdapter(List<Request> requestList, ClickListener clickListener, int type) {
        this.requestList = requestList;
        this.listener = clickListener;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donor_list, parent, false);

        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Request request = requestList.get(position);
        holder.requestId.setText(request.getRequestId()+"");
        holder.empName.setText(request.getEmpName());
        holder.bloodGroup.setText(request.getBloodGroup());
        holder.location.setText(request.getLocation());
        if(request.getIsCabService()){
            holder.isCabService.setText("Available");
        }
        else{
            holder.isCabService.setText("Not Available");
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

}