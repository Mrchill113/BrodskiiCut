package com.example.usermanagementmodule;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FirebaseServices fbs;
    ArrayList<Appointment> apts;
    RecyclerView rcApt;
    AptAdapter adapter;
    ImageView ivRefresh, ivAdmin, ivPFP;
    TextView tvUsername, tvHaircut, tvNumProfile;
    Utils utlis;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs= FirebaseServices.getInstance();
        utlis = Utils.getInstance();
        apts = new ArrayList<Appointment>();
        rcApt = getView().findViewById(R.id.rcAppointments);
        ivRefresh = getView().findViewById(R.id.ivRefresh);
        tvUsername = getView().findViewById(R.id.tvUsername);
        ivPFP = getView().findViewById(R.id.imageViewPFP);
        tvHaircut = getView().findViewById(R.id.tvHaircutsNum);
        ivAdmin = getView().findViewById(R.id.ivAdmin); ivAdmin.setVisibility(View.INVISIBLE);
        tvNumProfile = getView().findViewById(R.id.tvNumProfile);


        if(fbs.getUser()!=null) {
            tvUsername.setText(fbs.getUser().getUsername());
            tvNumProfile.setText(String.valueOf(fbs.getUser().getHaircuts()));
            if(fbs.getUser().getType().equals("admin")) {

                ivAdmin.setVisibility(View.VISIBLE);
                ivAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoToAdminPage();
                    }
                });

            }
        }

        fbs.getFire().collection("Appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {

                    Appointment appointment = dataSnapshot.toObject(Appointment.class);
                    if(appointment.getCustomer().equals(fbs.getAuth().getCurrentUser().getEmail())) apts.add(appointment);

                }

                SettingRecycler();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Couldn't Retrieve Appointments, Please Try Again Later!", Toast.LENGTH_SHORT).show();
            }
        });

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbs.getFire().collection("Appointments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        apts = new ArrayList<Appointment>();
                        for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {

                            Appointment appointment = dataSnapshot.toObject(Appointment.class);
                            if(appointment.getCustomer().equals(fbs.getAuth().getCurrentUser().getEmail())) apts.add(appointment);

                        }

                        SettingRecycler();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Couldn't Retrieve Appointments, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        ivPFP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChooser();
            }
        });

    }

    private void GoToAdminPage() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new AdminFragment());
        ft.commit();
    }

    private void SettingRecycler() {

        rcApt.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AptAdapter(getActivity(), apts);
        rcApt.setAdapter(adapter);

    }

    public void ImageChooser() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 123);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == getActivity().RESULT_OK && data!=null) {

            Uri selectedImageUri = data.getData();
            uploadImage(selectedImageUri);

        }

    }

    public void uploadImage(Uri selectedImageUri) {

        if (selectedImageUri != null) {

            String imageStr = "images/" + UUID.randomUUID() + ".jpg"; //+ selectedImageUri.getLastPathSegment();
            StorageReference imageRef = fbs.getStorage().getReference().child("images/image-removebg-preview.png");

            UploadTask uploadTask = imageRef.putFile(selectedImageUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            fbs.setSelectedImageURL(uri);
                            if(fbs.getUser() != null) updateImageinFire();
                            else Toast.makeText(getActivity(), "Couldn't Update Your Profile Photo, Try Again Later", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("Utils: uploadImage: ", e.getMessage());
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to Upload Image", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getActivity(), "Choose an Image", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateImageinFire(){

        String photo;
        if(fbs.getSelectedImageURL()==null) photo ="";
        else photo = fbs.getSelectedImageURL().toString()+".jpg";

        if(!photo.isEmpty()) {
            fbs.getFire().collection("Users").document(fbs.getAuth().getCurrentUser().getEmail()).update("pfp", photo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getActivity(), "Profile Photo Updated", Toast.LENGTH_LONG).show();
                    fbs.getUser().setPfp(photo);
                    fbs.setSelectedImageURL(null);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Couldn't Update Your Profile Photo, Try Again Later", Toast.LENGTH_LONG).show();
                }
            });
        }
        else Toast.makeText(getActivity(), "Press Your Profile Picture to Insert a new Image Firstly", Toast.LENGTH_LONG).show();

    }
}