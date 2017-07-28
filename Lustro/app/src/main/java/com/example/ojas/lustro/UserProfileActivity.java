package com.example.ojas.lustro;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserProfileAdapter mAdapter;

    private UserInformation userInformation;
    private MyGps myGps, userGps;


    String recieverUserId;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseReference;
    private String userLatitude, userLongitude;
    private Boolean flag1=false, flag2=false;
    private float distance;

    private TextView mTextViewName, mTextViewAge, mTextViewSex, mTextViewDistance, mTextViewUserGps, mTextViewMyGps;
    private List<String> urlList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Bundle bundle = getIntent().getExtras();
        recieverUserId = bundle.getString("chosenUser");

        //getActionBar().setTitle("User Profile");
        getSupportActionBar().setTitle("    User Profile");



        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewAge = (TextView)findViewById(R.id.textViewAge);
        mTextViewSex = (TextView) findViewById(R.id.textViewSex);
        mTextViewDistance = (TextView)findViewById(R.id.textViewDistance);
        mTextViewUserGps = (TextView) findViewById(R.id.textViewUserGps);
        mTextViewMyGps = (TextView)findViewById(R.id.textViewMyGps);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mAdapter = new UserProfileAdapter(urlList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mDatabaseReference.child(recieverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userInformation = dataSnapshot.child("UserInformation").getValue(UserInformation.class);
                userGps = dataSnapshot.child("MyGpsLocation").getValue(MyGps.class);
                mTextViewUserGps.setText("Lat. = " + userGps.getLatitude() + " Long. = " + userGps.getLongitude());
                flag1 = true;
                updateUrlList();
                updateUserInformation();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        mDatabaseReference.child(firebaseUser.getUid()).child("MyGpsLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myGps = dataSnapshot.getValue(MyGps.class);
                mTextViewMyGps.setText("Lat. = " + myGps.getLatitude() + " Long. = " + myGps.getLongitude());
                flag2 = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }


    public void updateUrlList()
    {
        if(userInformation.getUri1() .length() > 0)
        {urlList.add(userInformation.getUri1());}

        if(userInformation.getUri2() .length() > 0)
        {urlList.add(userInformation.getUri2());}

        if(userInformation.getUri3().length() > 0)
        {urlList.add(userInformation.getUri3());}

        if(userInformation.getUri4().length() > 0)
        {urlList.add(userInformation.getUri4());}

        if(userInformation.getUri5().length() > 0)
        {urlList.add(userInformation.getUri5());}

        mAdapter.notifyDataSetChanged();
    }
    public void updateUserInformation()
    {
        mTextViewName.setText(userInformation.getName());
        mTextViewSex.setText(userInformation.getSex());
        mTextViewAge.setText(userInformation.getAge());

        //userLatitude = myGps.getLatitude();
        //userLongitude = myGps.getLongitude();
    }
    public void onClickFindDistance(View view)
    {
        Location location1= new Location("");
        Location location2 = new Location("");
        //Log.d("findDistance", "currentUserLatitude = " + currentUserLatitude + "currentUserLongitude = " + currentUserLongitude );

        location1.setLatitude(Float.parseFloat(myGps.getLatitude()));
        location1.setLongitude(Float.parseFloat(myGps.getLongitude()));

        location2.setLatitude(Float.parseFloat(userGps.getLatitude()));
        location2.setLongitude(Float.parseFloat(userGps.getLongitude()));

        float distance = (location1.distanceTo(location2))/1000;
        mTextViewDistance.setText("Distance From User = " + String.format("%f Km",distance));
        return;
    }


}
