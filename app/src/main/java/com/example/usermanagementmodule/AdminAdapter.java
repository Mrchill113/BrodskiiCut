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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

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

                if(!apt.isApproved()) {

                    fbs.getFire().collection("Appointments").document(apt.getID()).update("approved", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            apts.get(position).setApproved(true);

                            fbs.getFire().collection("Users").document(apt.getCustomer()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    UserProfile user = documentSnapshot.toObject(UserProfile.class);
                                    fbs.getFire().collection("Users").document(apt.getCustomer()).update("haircuts", user.getHaircuts()+1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(context, "Action Completed!", Toast.LENGTH_SHORT).show();
                                            holder.tvApproved.setText("Yes");
                                            holder.tvApproved.setTextColor(Color.GREEN);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "Couldn't Update User Info, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Couldn't Retrieve User Info!", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Couldn't Complete Specified Action!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }

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
