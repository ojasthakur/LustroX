package com.example.ojas.lustro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.appcompat.R.styleable.View;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabaseReference;

    private RecyclerView recyclerView;
    private ChatActivityAdapter mAdapter;

    private List<String> myChatUsersList = new ArrayList<>();
    private List<MessageModel> messageList = new ArrayList<>();
    public String myUserId, recieverUserId;
    private EditText mEditTextMessage;
    private Button mButtonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle bundle = getIntent().getExtras();
        recieverUserId = bundle.getString("chosenUser");
        getSupportActionBar().setTitle("OnGoing Chat");

        mEditTextMessage = (EditText) findViewById(R.id.editTextMessage);
        mButtonSend = (Button) findViewById(R.id.buttonSendMessage);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ChatActivityAdapter(messageList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        myUserId = firebaseUser.getUid();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(myUserId).child("Messages").child(recieverUserId);
        Query queryRef = ref.orderByPriority();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                //UserInformation post1 = snapshot.child("UserInformation").getValue(UserInformation.class);
                MessageModel post = snapshot.getValue(MessageModel.class);

                messageList.add(post);
                mAdapter.notifyDataSetChanged();
                //Log.d("preinfo Fragment people", post1.getName() + " is " + post1.getSex() + " and the user id is "+snapshot.getKey());
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
            //for(int i = )

        });


    }


    public void sendMessage()
    {
        //Long tsLong = System.currentTimeMillis()/1000;
        //String ts = tsLong.toString();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String message = mEditTextMessage.getText().toString().trim();
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);

        messageModel.setFlag("Sent");
        ref.child(myUserId).child("Messages").child(recieverUserId).push().setValue(messageModel);

        messageModel.setFlag("Received");
        ref.child(recieverUserId).child("Messages").child(myUserId).push().setValue(messageModel);

        mEditTextMessage.setText("");
    }

    public void onClickOpenUserProfile(View view)
    {

        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra("chosenUser", recieverUserId);
        startActivity(intent);
    }

}
