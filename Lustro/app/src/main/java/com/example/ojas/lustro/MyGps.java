package com.example.ojas.lustro;

/**
 * Created by Ojas on 09-12-2016.
 */

public class MyGps {

    public String latitude="";
    public String longitude="";


    public MyGps(){}

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }
    public void setLongitude(String longitude) {this.longitude = longitude;}

    public String getLatitude(){return this.latitude;}
    public String getLongitude(){return this.longitude;}

}
