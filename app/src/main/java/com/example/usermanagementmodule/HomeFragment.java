package com.example.usermanagementmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ImageView ivBeard,ivHaircut,ivHairwash,ivHairdye;
    FirebaseServices fbs;
    TextView tvWelcomeHome, tvUserHome;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        fbs = FirebaseServices.getInstance();
        ivBeard = getView().findViewById(R.id.ivBeardHome);
        ivHaircut = getView().findViewById(R.id.ivHaircutHome);
        ivHairwash = getView().findViewById(R.id.ivHairwashHome);
        ivHairdye = getView().findViewById(R.id.ivHairdyeHome);
        tvUserHome = getView().findViewById(R.id.tvUserHome);
        tvWelcomeHome = getView().findViewById(R.id.tvWelcomeHome);

        if(fbs.getUser()!=null) {
            tvUserHome.setText(fbs.getUser().getUsername());
        }


        if(fbs.getUser() == null) {

            fbs.getFire().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    UserProfile user = documentSnapshot.toObject(UserProfile.class);
                    fbs.setUser(user);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(), "Couldn't Retrieve User Info, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                    fbs.setUser(null);

                }
            });

        }



        ivBeard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new SelectBarberFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Service", "Beard Trimming");
                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivHaircut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new SelectBarberFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Service", "Haircut");
                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivHairwash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new SelectBarberFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Service", "Hairwash");
                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivHairdye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new SelectBarberFragment();
                Bundle bundle = new Bundle();
                bundle.putString("Service", "Hairdye");
                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });

    }
}