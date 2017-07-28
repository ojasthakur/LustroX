package com.example.ojas.lustro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //making all these variables private limits their usage only to this class, and hence not acces
    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button getButtonSignIn;
    private Button buttonSignUpScreen;


    private ProgressDialog progressDialog;
    private FirebaseAuth mFirebaseAuth;   // This is an auth object, which will take care of out login businss
    private FirebaseUser mFirebaseUser;   // This is a firebaseUser object which will house the data from the database
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        getButtonSignIn   =    (Button) findViewById(R.id.buttonSignIn);  //cant seem to understant the use of these, as button directly calls function
        buttonSignUpScreen=    (Button) findViewById(R.id.buttonSignUpScreen);


        progressDialog = new ProgressDialog(this);
        mFirebaseAuth = mFirebaseAuth.getInstance(); //Gets us the current state of the authentication


        //below code shall be run if someone has already logged in
        if(mFirebaseAuth.getCurrentUser() != null)
        {
            finish(); // this method will close the current activity
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }



    public void onClickButtonSignIn(View view)
    {
        userLogin();
    }

    public void onClickButtonSignUpActivity(View view)
    {
        finish();
        startActivity(new Intent(this, SignUpActivity.class));
    }



    private void userLogin()
    {
        editTextEmail    =    (EditText) findViewById(R.id.editTextEmail);
        editTextPassword =    (EditText) findViewById(R.id.editTextPassword);

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(LoginActivity.this, "Valid Email is Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(LoginActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging In :: Internet Required");
        progressDialog.show();

        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            //start the profile activity
                            //as we are in the onComplete wait listner we cannot passs "this"
                            //So we will use getApplicationContext() method
                            finish(); // this method will close the current activity
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Could Not Login::Please try again", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }


}


