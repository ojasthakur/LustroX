package com.example.ojas.lustro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.INTEGER;


public class UpdateProfileActivity extends AppCompatActivity {

    private ImageButton mSelectImage1, mSelectImage2, mSelectImage3, mSelectImage4, mSelectImage5;
    private Spinner mSpinnerAge,mSpinnerSex;
    private EditText mEditTextName;
    private String userId;
    private UserInformation userInformation = new UserInformation();
    private String imageUri1="", imageUri2="", imageUri3="", imageUri4="", imageUri5="";
    public  String userName,userAge, userSex;

    private StorageReference mStorage; // creating a firebase storage reference for all tasks like uploading, deleting, downloading etc//basically a pointer to a file in the cloud
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseReference;
    private FirebaseStorage storage;
    private static final int GALLERY_INTENT1 = 1, GALLERY_INTENT2 = 2, GALLERY_INTENT3 = 3, GALLERY_INTENT4 = 4, GALLERY_INTENT5 = 5;
    private ProgressDialog progressDialog;

    //private boolean flag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle("Update Profile Area");
        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();

        mStorage = FirebaseStorage.getInstance().getReference(); // returns the root directory of the firebase storage
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mDatabaseReference.child(userId).child("UserInformation").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UserInformation user = dataSnapshot.getValue(UserInformation.class);

                userName = user.getName();
                userSex = user.getSex();
                userAge = user.getAge();
                int temp1 = Integer.parseInt(userAge);
                temp1= temp1 -18;




                imageUri1 = user.getUri1();
                imageUri2 = user.getUri2();
                imageUri3 = user.getUri3();
                imageUri4 = user.getUri4();
                imageUri5 = user.getUri5();

                mEditTextName.setText(userName);
                mSpinnerAge.setSelection(temp1);


                if(userSex.equals("Male"))
                {
                    int temp2=0;
                    mSpinnerSex.setSelection(temp2);
                }

                else if (userSex .equals("Female"))
                {
                    int temp2 = 1;
                    mSpinnerSex.setSelection(temp2);
                }
                //Uri loadUri = Uri.parse(imageUri1);
                Picasso.with(UpdateProfileActivity.this).load( Uri.parse(imageUri1)).fit().centerCrop().into(mSelectImage1);
                Picasso.with(UpdateProfileActivity.this).load( Uri.parse(imageUri2)).fit().centerCrop().into(mSelectImage2);
                Picasso.with(UpdateProfileActivity.this).load( Uri.parse(imageUri3)).fit().centerCrop().into(mSelectImage3);
                Picasso.with(UpdateProfileActivity.this).load( Uri.parse(imageUri4)).fit().centerCrop().into(mSelectImage4);
                Picasso.with(UpdateProfileActivity.this).load( Uri.parse(imageUri5)).fit().centerCrop().into(mSelectImage5);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }

        });





        //ImageButtons
        mSelectImage1 = (ImageButton) findViewById(R.id.imageButton1);
        mSelectImage2 = (ImageButton) findViewById(R.id.imageButton2);
        mSelectImage3 = (ImageButton) findViewById(R.id.imageButton3);
        mSelectImage4 = (ImageButton) findViewById(R.id.imageButton4);
        mSelectImage5 = (ImageButton) findViewById(R.id.imageButton5);
        mEditTextName = (EditText)    findViewById(R.id.editTextName);
        //mEditTextName.setText(userName);

        progressDialog = new ProgressDialog(this);
        mStorage = FirebaseStorage.getInstance().getReference(); // returns the root directory of the firebase storage





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


    public void onClickImageButton1(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        this.startActivityForResult(intent, GALLERY_INTENT1);
        //The intent sends the filepath of the selected image to the activity being called
        //there it can be extracted using data.getData()
    }

    public void onClickImageButton2(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        this.startActivityForResult(intent, GALLERY_INTENT2);

    }

    public void onClickImageButton3(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        this.startActivityForResult(intent, GALLERY_INTENT3);

    }

    public void onClickImageButton4(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        this.startActivityForResult(intent, GALLERY_INTENT4);

    }

    public void onClickImageButton5(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        this.startActivityForResult(intent, GALLERY_INTENT5);

    }



    public void onClickButtonSave(View view)
    {
        saveUserInformation();
    }

    private void saveUserInformation()
    {//first we will get the values from the edit texts
        userName = mEditTextName.getText().toString().trim();
        userSex = String.valueOf(mSpinnerSex.getSelectedItem());
        userAge = String.valueOf(mSpinnerAge.getSelectedItem());

        //now we will define an object for the class we created :: userinfromation

        userInformation.setName(userName);
        userInformation.setAge(userAge);
        userInformation.setSex(userSex);
        userInformation.setUri1(imageUri1);
        userInformation.setUri2(imageUri2);
        userInformation.setUri3(imageUri3);
        userInformation.setUri4(imageUri4);
        userInformation.setUri5(imageUri5);
        //FirebaseUser user = mFirebaseAuth.getCurrentUser();

        //to store the user info in firebase we can use the unique id of the logged in user, which we can get with the auth object
        //we can create a node inside the firebase database with that unique id
        mDatabaseReference.child(firebaseUser.getUid()).child("UserInformation").setValue(userInformation);
        Toast.makeText(this, "Information saved", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT1 && resultCode == RESULT_OK) {
            Uri uri1 = data.getData(); //

            StorageReference filePath1 = mStorage.child(userId).child("Photo1").child(uri1.getLastPathSegment());
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();
            filePath1.putFile(uri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();


                    // StorageReference deleteReference = storage.getReferenceFromUrl(imageUri1);

                    imageUri1 = downloadUri.toString();
                    saveUserInformation();
                    Picasso.with(UpdateProfileActivity.this).load(downloadUri).fit().centerCrop().into(mSelectImage1);
                    //deleteReference.delete();


                }
            });
        }
        if (requestCode == GALLERY_INTENT2 && resultCode == RESULT_OK) {
            Uri uri2 = data.getData();

            StorageReference filePath2 = mStorage.child(userId).child("Photo2").child(uri2.getLastPathSegment());
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();
            filePath2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUri2 = downloadUri.toString();
                    saveUserInformation();
                    Picasso.with(UpdateProfileActivity.this).load(downloadUri).fit().centerCrop().into(mSelectImage2);

                }
            });
        }
        if (requestCode == GALLERY_INTENT3 && resultCode == RESULT_OK) {
            Uri uri3 = data.getData();

            StorageReference filePath3 = mStorage.child(userId).child("Photo3").child(uri3.getLastPathSegment());
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();
            filePath3.putFile(uri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUri3 = downloadUri.toString();
                    saveUserInformation();
                    Picasso.with(UpdateProfileActivity.this).load(downloadUri).fit().centerCrop().into(mSelectImage3);


                }
            });
        }
        if (requestCode == GALLERY_INTENT4 && resultCode == RESULT_OK) {
            Uri uri4 = data.getData();

            StorageReference filePath4 = mStorage.child(userId).child("Photo4").child(uri4.getLastPathSegment());
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();
            filePath4.putFile(uri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUri4 = downloadUri.toString();
                    saveUserInformation();
                    Picasso.with(UpdateProfileActivity.this).load(downloadUri).fit().centerCrop().into(mSelectImage4);


                }
            });
        }
        if (requestCode == GALLERY_INTENT5 && resultCode == RESULT_OK)
        {
            Uri uri5 = data.getData();
            StorageReference filePath5 = mStorage.child(userId).child("Photo5").child(uri5.getLastPathSegment());
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();
            filePath5.putFile(uri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
            {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    progressDialog.dismiss();
                    Toast.makeText(UpdateProfileActivity.this, "Upload Done", Toast.LENGTH_LONG).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUri5 = downloadUri.toString();
                    saveUserInformation();
                    Picasso.with(UpdateProfileActivity.this).load(downloadUri).fit().centerCrop().into(mSelectImage5);


                }
            });
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        //finish(); // this method will close the current activity
        //startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }



}