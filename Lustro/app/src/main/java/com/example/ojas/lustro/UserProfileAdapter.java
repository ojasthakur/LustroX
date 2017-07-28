package com.example.ojas.lustro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Ojas on 14-12-2016.
 */



public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.MyViewHolder>  {
    private List<String> urlList;




        @Override
    public UserProfileAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_profile,parent,false);

        return new UserProfileAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserProfileAdapter.MyViewHolder holder, int position)
    {
        String url = urlList.get(position);


        if(url != null)

        {Picasso.with(holder.mImageViewUserPicture.getContext()).load( Uri.parse(url)).placeholder(R.mipmap.lustrofinal).fit().centerCrop().into(holder.mImageViewUserPicture);}
    }

    @Override
    public int getItemCount()
    {
        return urlList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageViewUserPicture;

        public MyViewHolder(View view)
        {
            super(view);
            mImageViewUserPicture = (ImageView)view.findViewById(R.id.imageViewUserPicture);
        }

    }
    //Constructor for the whole userInformationAdapterClass
    public UserProfileAdapter(List<String> urlList)
    {
        this.urlList = urlList;
    }

}
