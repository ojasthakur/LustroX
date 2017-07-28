package com.example.ojas.lustro;

import android.content.Context;
import android.content.Intent;
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
 * Created by Ojas on 12-12-2016.
 */

public class ChatActivityAdapter extends RecyclerView.Adapter<ChatActivityAdapter.MyViewHolder> {

    List<MessageModel> messageList;


    @Override
    public ChatActivityAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_chat,parent,false);

        return new ChatActivityAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ChatActivityAdapter.MyViewHolder holder, int position)
    {



        MessageModel messageModel = messageList.get(position);
        holder.message.setText(messageModel.getMessage());
        holder.messageSender.setText(messageModel.getFlag());

    }

    @Override
    public int getItemCount()
    {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView message, messageSender;

        public MyViewHolder(View view)
        {
            super(view);

            message = (TextView) view.findViewById(R.id.textViewMessage);
            messageSender = (TextView) view.findViewById(R.id.textViewMessageSender);

        }

    }
    //Constructor for the whole userInformationAdapterClass
    public ChatActivityAdapter(List<MessageModel> messageList)
    {
        this.messageList = messageList;
        //this.context = context;

        //Log.d("contructor infoAdapter", "currentUserLatitude = " + currentUserLatitude + "currentUserLongitude = " + currentUserLongitude );
    }
}
