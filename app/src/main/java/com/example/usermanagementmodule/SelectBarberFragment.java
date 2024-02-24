package com.example.usermanagementmodule;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectBarberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectBarberFragment extends Fragment {

    String Service;
    ImageView ivJacob, ivJojo, ivKing, ivTarbee3;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectBarberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectBarberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectBarberFragment newInstance(String param1, String param2) {
        SelectBarberFragment fragment = new SelectBarberFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_barber, container, false);

        Bundle bundle = this.getArguments();

        Service = bundle.getString("Service");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ivJacob = getView().findViewById(R.id.ivJacob);
        ivJojo = getView().findViewById(R.id.ivJojo);
        ivKing = getView().findViewById(R.id.ivKing);
        ivTarbee3 = getView().findViewById(R.id.ivTarbee3);


        ivJacob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new AppointmentFragment();
                Bundle bundle = new Bundle();


                bundle.putString("Service", Service);
                bundle.putString("Barber", "Jacob");


                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivJojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new AppointmentFragment();
                Bundle bundle = new Bundle();


                bundle.putString("Service", Service);
                bundle.putString("Barber", "Jojo");


                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivKing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new AppointmentFragment();
                Bundle bundle = new Bundle();


                bundle.putString("Service", Service);
                bundle.putString("Barber", "King");


                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });


        ivTarbee3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment gtn = new AppointmentFragment();
                Bundle bundle = new Bundle();


                bundle.putString("Service", Service);
                bundle.putString("Barber", "Tarbee3");


                gtn.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayoutMain, gtn);
                ft.commit();
            }
        });

    }

}