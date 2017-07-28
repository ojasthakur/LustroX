package com.example.ojas.lustro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Ojas on 30-11-2016.
 */

public class FragmentUserAccount extends Fragment
{

    public static FragmentUserAccount newInstance()
    {
        FragmentUserAccount mFragment = new FragmentUserAccount();  //FragmentUserAccount is the contructor method for our class
        return mFragment;
    }

    public FragmentUserAccount()
    {
        //let it stay empty and public
    }


    private TextView textViewWelcomeMessage;
    private Button mButtonLogout;
    public ImageView mImageViewProfilePic;


    private String userId;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorage;
    private FirebaseUser firebaseUser;
    private UserInformation userInformation = new UserInformation();
    private String imageUri1="", imageUri2="", imageUri3="", imageUri4="", imageUri5="";
    /*the following is the most important method of our fragment
    *links the fragment to its layout
    * connects our buttons and textviews using findViewById
    */
    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup mContainer, Bundle mSavedInstanceState)
    {
        //INFLATES LAYOUT
        View mRootView = mInflater.inflate(R.layout.fragment_user_account, mContainer, false);
        textViewWelcomeMessage = (TextView) mRootView.findViewById(R.id.textViewWelcomeMessage);


        //ATTACH BUTTONS TO XML
        mButtonLogout = (Button) mRootView.findViewById(R.id.buttonLogout);
        final ImageView mImageViewProfilePic = (ImageView) mRootView.findViewById(R.id.imageViewProfilePic);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        //SET UP FIREBASE OBJECTS
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference(); // returns the root directory of the firebase storage
        userId = firebaseUser.getUid();



        //GENERATE CURRENT USER PROFILE WITH FIREBASE SNAPSHOT
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog.setMessage("Getting Current User Information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        mDatabaseReference.child(userId).child("UserInformation").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                userNamex = user.getName();
                userSex = user.getSex();
                userAge = user.getAge();

                imageUri1 = user.getUri1();
                //imageUri2 = user.getUri2();
                //imageUri3 = user.getUri3();
                //imageUri4 = user.getUri4();
                //imageUri5 = user.getUri5();
                textViewWelcomeMessage.setText("Welcome "+ userNamex);

                if(FragmentUserAccount.super.getActivity() != null) {
                    Picasso.with(FragmentUserAccount.super.getActivity()).load(Uri.parse(imageUri1)).fit().centerCrop().transform(new CropCircleTransformation()).into(mImageViewProfilePic);
                }
                Log.d("FragmentUserAccount", " user account info updating ");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        if(mFirebaseAuth.getCurrentUser() == null)
        {
            //user is not logged in
            getActivity().finish();  //when you are not in the activity class use getActivity to get the activity reference
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }


        final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        userId = firebaseUser.getUid();

        ((MainActivity)getActivity()).setCurrentUserId(userId);



        return mRootView;
    }


    public  String userNamex,userAge, userSex;




    public void onClickButtonLogout(View view)
    {
        mFirebaseAuth.signOut();
        getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
    //This is the end of FragmentUserAccountClass
}
