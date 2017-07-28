package com.example.ojas.lustro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserInitializeActivity extends AppCompatActivity {

    private Spinner mSpinnerAge,mSpinnerSex;
    private EditText mEditTextUserName;
    private String userId;
    private  String userName,userAge, userSex;
    private UserInformation userInformation = new UserInformation();
    private  MyGps myGps = new MyGps();



    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_initialize);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mEditTextUserName = (EditText)    findViewById(R.id.editTextUserName);




        List ageArray = new ArrayList<Integer>();
        for(int i = 18; i <100; i++)
        {
            ageArray.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> ageAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,ageArray);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAge = (Spinner) findViewById(R.id.spinnerAge);
        mSpinnerAge.setAdapter(ageAdapter);






        List<String> sexArray = new ArrayList<>();
        sexArray.add("Male");
        sexArray.add("Female");

        ArrayAdapter<String> sexAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,sexArray);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSex = (Spinner) findViewById(R.id.spinnerSex);
        mSpinnerSex.setAdapter(sexAdapter);
    }

    public void onClickButtonSaveUserInformation(View view)
    {//first we will get the values from the edit texts
        userName = mEditTextUserName.getText().toString().trim();
        userSex = String.valueOf(mSpinnerSex.getSelectedItem());
        userAge = String.valueOf(mSpinnerAge.getSelectedItem());

        //now we will define an object for the class we created :: userinfromation

        userInformation.setName(userName);
        userInformation.setAge(userAge);
        userInformation.setSex(userSex);
        userInformation.setUri1("");
        userInformation.setUri2("");
        userInformation.setUri3("");
        userInformation.setUri4("");
        userInformation.setUri5("");
        //FirebaseUser user = mFirebaseAuth.getCurrentUser();

        //to store the user info in firebase we can use the unique id of the logged in user, which we can get with the auth object
        //we can create a node inside the firebase database with that unique id
        mDatabaseReference.child(firebaseUser.getUid()).child("UserInformation").setValue(userInformation);
        Toast.makeText(this, "Information saved", Toast.LENGTH_LONG).show();


        finish();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));

    }
}
