package com.example.ojas.lustro;

import android.net.Uri;

/**
 * Created by Ojas on 20-10-2016.
 */
//here you define the data you want to be stored in the firebase
//the access modifiers of these variables should always be public
public class UserInformation
{
    public String name;
    public String sex;
    public String age;

    public String uri1 = "", uri2="", uri3="", uri4="", uri5="";
    //public String myGps"";

    //default constructor
    public UserInformation()
    {

    }

    public void setName(String name)
    {this.name = name;}
    public void setSex(String sex)
    {this.sex = sex;}
    public void setAge(String age)
    {this.age = age;}
    public void setUri1(String uri1)
    {this.uri1 = uri1;}
    public void setUri2(String uri2)
    {this.uri2 = uri2;}
    public void setUri3(String uri3)
    {this.uri3 = uri3;}
    public void setUri4(String uri4)
    {this.uri4 = uri4;}
    public void setUri5(String uri5)
    {this.uri5 = uri5;}
    /*public void setMyGps(String myGps)
    {
        this.myGps = myGps;
    }*/

    public String getName()
    {return this.name;}
    public String getSex()
    {return this.sex;}
    public String getAge()
    {return this.age;}
    public String getUri1()
    {return this.uri1;}
    public String getUri2()
    {return this.uri2;}
    public String getUri3()
    {return this.uri3;}
    public String getUri4()
    {return this.uri4;}
    public String getUri5()
    {return this.uri5;}
    /*public String getMyGps()
    {
        return this.myGps;
    }*/

}
