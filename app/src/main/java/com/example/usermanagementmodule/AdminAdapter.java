package com.example.usermanagementmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdmnHolder> {

    private Context context;
    private ArrayList<Appointment> apts;
    private FirebaseServices fbs;

    public AdminAdapter(Context context, ArrayList<Appointment> apts) {
        fbs = FirebaseServices.getInstance();
        this.context = context;
        this.apts = apts;
    }

    public AdminAdapter() {
    }

    @NonNull
    @Override
    public AdminAdapter.AdmnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item,parent,false);

        return new AdminAdapter.AdmnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.AdmnHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment apt = apts.get(position);
        holder.SetDetails(apt);

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return apts.size();
    }


    class AdmnHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvService, tvTime, tvApproved;
        private Button btn;

        public AdmnHolder (@NonNull View itemView) {
            super(itemView);

            btn = itemView.findViewById(R.id.btnApprove);
            tvName = itemView.findViewById(R.id.BarberName);
            tvService = itemView.findViewById(R.id.AptService);
            tvTime = itemView.findViewById(R.id.AptTime);
            tvApproved = itemView.findViewById(R.id.Approved);

        }

        void SetDetails (Appointment apt){

            tvName.setText("Name: " + apt.getBarber());
            tvService.setText("Service: " + apt.getService());
            tvTime.setText("Time: " + apt.getDateTime());

            if(apt.isApproved()) {
                tvApproved.setText("Yes");
                tvApproved.setTextColor(Color.GREEN);
            }
            else {
                tvApproved.setText("No");
                tvApproved.setTextColor(Color.RED);
            }

        }
    }

}
