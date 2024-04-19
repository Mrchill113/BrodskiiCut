package com.example.usermanagementmodule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseServices fbs;
    String email;
    UserProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbs = FirebaseServices.getInstance();


        if(fbs.getAuth().getCurrentUser() != null) {

            email = fbs.getAuth().getCurrentUser().getEmail();
            fbs.getFire().collection("Users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    user = documentSnapshot.toObject(UserProfile.class);
                    fbs.setUser(user);
                    gotoHomeFragment();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // user will still be null!
                    Toast.makeText(MainActivity.this, "Couldn't Retrieve User Data, try again later!", Toast.LENGTH_LONG).show();
                }
            });

        }
        else gotoLoginFragment();

    }

    private void gotoAdminFragment(){

    }

    private void gotoHomeFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new HomeFragment());
        ft.commit();
    }

    private void gotoLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new LoginFragment());
        ft.commit();
    }

}