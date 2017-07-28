package com.example.ojas.lustro;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Ojas on 11-12-2016.
 */

public class UserInformationAdapter extends RecyclerView.Adapter<UserInformationAdapter.MyViewHolder> {

    private List<UserInformation> userList;
    private List<MyGps> userGps;
    private List<String> userIdList;
    private String currentUserLatitude;
    private String currentUserLongitude;
    private Context context;
    private View v;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_fragment_people,parent,false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        String userId = userIdList.get(position);
        holder.userId.setText(userId);


        UserInformation userInformation = userList.get(position);
        holder.userName.setText(userInformation.getName());
        holder.userSex.setText(userInformation.getSex());
        holder.userAge.setText(userInformation.getAge());

        MyGps currentGps = userGps.get(position);
        //float distance = findDistance(currentGps.getLatitude(), currentGps.getLongitude());

        //holder.imageUri.setText("Distance = " + String.format("%f",distance));
        Picasso.with(holder.imageViewProfilePic.getContext()).load( Uri.parse(userInformation.getUri1())).placeholder(R.mipmap.lustrofinal).fit().centerCrop().transform(new CropCircleTransformation()).into(holder.imageViewProfilePic);
    }

    @Override
    public int getItemCount()
    {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView userName, userAge, userSex, imageUri, userId;
        public ImageView imageViewProfilePic;

        public MyViewHolder(View view)
        {
            super(view);
            v = view;
            userName = (TextView) view.findViewById(R.id.textViewMessage);
            userAge = (TextView) view.findViewById(R.id.textViewUserAge);
            userSex = (TextView)view.findViewById(R.id.textViewUserSex);
            imageUri = (TextView)view.findViewById(R.id.textViewImageUri);
            userId = (TextView)view.findViewById(R.id.textViewUserId);

            imageViewProfilePic = (ImageView)view.findViewById(R.id.imageViewCard);

            v.setOnClickListener(this);
        }
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("chosenUser", userId.getText());
            context.startActivity(intent);
        }
    }
    //Constructor for the whole userInformationAdapterClass
    public UserInformationAdapter(List<UserInformation> userList, List<MyGps> userGps,List<String> userIdList, Context context)
    {
        this.userList = userList;
        this.userGps = userGps;
        this.userIdList = userIdList;
        this.context = context;

//        Log.d("contructor infoAdapter", "currentUserLatitude = " + currentUserLatitude + "currentUserLongitude = " + currentUserLongitude );
    }





}
