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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {


    //Object Declarations below

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button mButtonRegister;
    private Button mButtonSignIn;

    private ProgressDialog progressDialog;  //Inititalizing the new progress dialog object//can be used multiple times?
    private FirebaseAuth mFirebaseAuth;      //This is the firebase authentication object








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up Here");

        mButtonRegister   =    (Button) findViewById(R.id.buttonRegister);
        mButtonSignIn     =    (Button) findViewById(R.id.buttonSignIn);

        progressDialog = new ProgressDialog(this);
        mFirebaseAuth= FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() != null)
        {
            //indirectly start the profile activity here
            finish(); // this method will close the current activity
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerUser();
            }
        });

    }






    public void onClickButtonSignInActivity(View view)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void registerUser()  //Function will get the email and passwords from the login screen and check if they are empty
    {
        editTextEmail    =    (EditText) findViewById(R.id.editTextEmail);
        editTextPassword =    (EditText) findViewById(R.id.editTextPassword);

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(SignUpActivity.this, "Valid Email is Required", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //password is empty
            Toast.makeText(SignUpActivity.this, "Password Cannot Be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        //When Code Reaches here we now register the user
        progressDialog.setMessage("Registering user :: Internet Required");
        progressDialog.show();

        mFirebaseAuth.createUserWithEmailAndPassword(email, password)  // we shall use a listener to check if the registration is complete
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //user is successfully registered
                            //now we can the start the profile activity
                            Toast.makeText(SignUpActivity.this, "Registered Successfully",Toast.LENGTH_SHORT);
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserInitializeActivity.class));


                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this, "Could Not Register::Please try again", Toast.LENGTH_SHORT);
                        }
                    }
                });
    }
}

