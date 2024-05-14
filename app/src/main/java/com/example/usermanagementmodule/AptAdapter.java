package com.example.usermanagementmodule;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AptAdapter extends RecyclerView.Adapter<AptAdapter.AptHolder> {

    private Context context;
    private ArrayList<Appointment> apts;
    private FirebaseServices fbs;

    public AptAdapter(Context context, ArrayList<Appointment> apts) {
        this.context = context;
        this.apts = apts;
    }

    public AptAdapter() {
    }

    @NonNull
    @Override
    public AptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_item,parent,false);

        return new AptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AptAdapter.AptHolder holder, @SuppressLint("RecyclerView") int position) {

        Appointment apt = apts.get(position);
        holder.SetDetails(apt);

    }

    @Override
    public int getItemCount() {
        return apts.size();
    }


    class AptHolder extends RecyclerView.ViewHolder {

        private TextView tvName, tvService, tvTime, tvApproved;
        private ImageView ivBarber;

        public AptHolder (@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.BarberName);
            tvService = itemView.findViewById(R.id.AptService);
            tvTime = itemView.findViewById(R.id.AptTime);
            tvApproved = itemView.findViewById(R.id.Approved);
            ivBarber = itemView.findViewById(R.id.ivBarberItem);

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

            String name = apt.getBarber();
            if(name.equals("Jacob")) ivBarber.setImageResource(R.drawable.jacob);
            else if(name.equals("King")) ivBarber.setImageResource(R.drawable.toto);
            else if(name.equals("Jojo")) ivBarber.setImageResource(R.drawable.jojo);
            else if(name.equals("Tarbee3")) ivBarber.setImageResource(R.drawable.tarbee3);

        }
    }

}
