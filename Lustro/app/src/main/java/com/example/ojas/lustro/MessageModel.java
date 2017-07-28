package com.example.ojas.lustro;

/**
 * Created by Ojas on 12-12-2016.
 */

public class MessageModel {

    String message;
    String flag;

    public MessageModel()
    {

    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFlag(String flag){ this.flag = flag;}

    public String getMessage() {
        return message;
    }

    public String getFlag() {
        return flag;
    }
}
