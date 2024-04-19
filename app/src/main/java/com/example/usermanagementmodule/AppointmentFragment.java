package com.example.usermanagementmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.type.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentFragment extends Fragment {

    FirebaseServices fbs;
    private String selectedDateTimeStr;
    Button btnBook;
    ImageView ivBarber;
    TextView tvBarberName;
    private DatePicker datePick;
    private TimePicker timePick;
    private String Barber, Service;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentFragment newInstance(String param1, String param2) {
        AppointmentFragment fragment = new AppointmentFragment();
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
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);
        Bundle bundle = this.getArguments();

        Service = bundle.getString("Service");
        Barber = bundle.getString("Barber");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        fbs = FirebaseServices.getInstance();
        btnBook = getView().findViewById(R.id.btnBook);
        ivBarber = getView().findViewById(R.id.ivBarber);
        tvBarberName = getView().findViewById(R.id.tvBarberName);
        datePick = getView().findViewById(R.id.datePicker);
        timePick = getView().findViewById(R.id.timePicker);


        // Apply Chosen Barber Name and Photo..................

        tvBarberName.setText(Barber);

        if(Barber.equals("Jacob")) ivBarber.setImageResource(R.drawable.jacob);
        else if(Barber.equals("Jojo")) ivBarber.setImageResource(R.drawable.jojo);
        else if(Barber.equals("King")) ivBarber.setImageResource(R.drawable.toto);
        else ivBarber.setImageResource(R.drawable.tarbee3);

        // Ends...........................

        DateTime dt = DateTime.newBuilder().build();
        int hour = dt.getHours();
        int minute = dt.getMinutes();

        timePick.setHour(hour);
        timePick.setMinute(minute);


        datePick.setMinDate(System.currentTimeMillis() - 1000);
        setMinTimePicker();


        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setCurrentDateTimeStr();

                BookAppointment(Barber, Service, selectedDateTimeStr);

            }
        });

    }

    public void setMinTimePicker()
    {
        // Set the minimum time to the current time
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        timePick.setHour(currentHour);
        timePick.setMinute(currentMinute);
    }

    private void setCurrentDateTimeStr() {
        int year = datePick.getYear();
        int month = datePick.getMonth();
        int dayOfMonth = datePick.getDayOfMonth();
        int hour = timePick.getHour();
        int minute = timePick.getMinute();
        Calendar selectedDateTime = Calendar.getInstance();
        selectedDateTime.set(year, month, dayOfMonth, hour, minute);

        //selectedDateTimeStr = selectedDateTime.getTime().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        selectedDateTimeStr = dateFormat.format(selectedDateTime.getTime());
    }

    private void BookAppointment(String barber, String service, String datetime){

        String email = fbs.getAuth().getCurrentUser().getEmail();
        Appointment appointment = new Appointment(email,barber,service,datetime,false);

        fbs.getFire().collection("Appointments").add(appointment).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(getActivity(), "Appointment Successfully Booked: " + barber + ", " + service + ", " + datetime, Toast.LENGTH_SHORT).show();
                GoToProfile();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getActivity(), "Couldn't Complete Booking, Try Again Later!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void GoToProfile() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutMain, new ProfileFragment());
        ft.commit();
    }

    private boolean isInLimitRange(Appointment ap) {
        //  ArrayList<TimeLimit> limits;
        //limits = fbs.getTls();
        //if (limits == null || limits.size() == 0)
        //  return true;
        //for(TimeLimit tl : limits)
        //{
        //  if (ap.getBarber().equals(tl.getBarber()))
        //{
        //  try {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //    Date selectedTime = dateFormat.parse(selectedDateTimeStr);
        //  if (tl.getStartTime() < selectedTime.getHours() &&
        //        tl.getEndTime() >= selectedTime.getHours())
        return true;
        //System.out.println(newConvertedDate2);
        //} catch (Exception e) {
        //  System.out.println(e.getLocalizedMessage());
        //}
        //}
    }
}