package com.example.ojas.lustro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;

import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;


//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Ojas on 08-12-2016.
 */

//public class FragmentPeople {}

public class FragmentPeople extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static FragmentPeople newInstance() {
        FragmentPeople mFragment = new FragmentPeople();  //FragmentUserAccount is the contructor method for our class
        return mFragment;
    }
    public FragmentPeople() {
        //let it stay empty and public
    }

    protected static final String TAG = "MainActivity";
    //Provides the entry point to Google Play services.

    protected GoogleApiClient mGoogleApiClient;

    //Represents a geographical location/
    protected Location mLastLocation;
    private MyGps myGps = new MyGps();

    //FirebaseElements
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseReference;

    // UI elements
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;


    //Class Variables
    String userId;
    private List<UserInformation> userList = new ArrayList<UserInformation>();
    private List<MyGps> userGps= new ArrayList<>();
    private  List <String> userIdList = new ArrayList<>();

    private RecyclerView recyclerView;
    private UserInformationAdapter mAdapter;

    private int i;
    private String currentUserID;
    private String currentUserLatitude, currentUserLongitude;
    private ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup mContainer, Bundle mSavedInstanceState) {

        View mRootView = mInflater.inflate(R.layout.fragment_people, mContainer, false);

        progressDialog = new ProgressDialog(getContext());

        recyclerView = (RecyclerView)mRootView.findViewById(R.id.recycler_view);

        mAdapter = new UserInformationAdapter(userList, userGps,userIdList, getContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        currentUserID = ((MainActivity)getActivity()).getCurrentUserId();
        progressDialog.setMessage("Getting Current User Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mDatabaseReference.child(userId).child("MyGpsLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                MyGps currentUserGps = dataSnapshot.getValue(MyGps.class);
                currentUserLatitude = currentUserGps.getLatitude();
                currentUserLongitude = currentUserGps.getLongitude();
                Log.d("updating gps current", "currentUserLatitude = " + currentUserLatitude + " current longitude" + currentUserLongitude);
                mAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        buildGoogleApiClient();


        //Firebase ref = new Firebase("https://lustro-5389c.firebaseio.com/");
        //Query queryRef = ref.orderByChild("UserInformation/sex");








        //Toolbar toolbar = (Toolbar)mRootView.findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        prepareUserInformation();

        return mRootView;
    }

    private void prepareUserInformation()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query queryRef = ref.orderByChild("UserInformation/sex");
        progressDialog.setMessage("Getting Current User Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                UserInformation post1 = snapshot.child("UserInformation").getValue(UserInformation.class);
                String userId = snapshot.getKey();

                userIdList.add(userId);
                userList.add(post1);
                MyGps post2 = snapshot.child("MyGpsLocation").getValue(MyGps.class);
                userGps.add(post2);

                mAdapter.notifyDataSetChanged();

                //System.out.println(post.getName() + " is " + post.getSex() + " the user id is " + snapshot.getKey());
                //mTextViewData.setText(snapshot.getKey() + snapshot.getChildren());
                Log.d("preinfo Fragment people", post1.getName() + " is " + post1.getSex() + " and the user id is "+snapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
            // ....

        });
        progressDialog.dismiss();

    }











    public void setMyGps()
    {
        mDatabaseReference.child(userId).child("MyGpsLocation").setValue(myGps);

    }
    //Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            //mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
            //mLastLocation.getLatitude()));

            myGps.setLatitude(String.format("%f",mLastLocation.getLatitude()));

            //mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
            //mLastLocation.getLongitude()));

            myGps.setLongitude(String.format("%f",mLastLocation.getLongitude()));
            setMyGps();
        } else {
            Toast.makeText(getContext(),R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

}
