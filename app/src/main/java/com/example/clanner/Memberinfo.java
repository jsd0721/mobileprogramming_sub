package com.example.clanner;

import android.widget.EditText;

public class Memberinfo {

    private String name;
    //private String photoUrl;

    public Memberinfo(String name){
        this.name = name;
        //,String photoUrl
        //this.photoUrl=photoUrl;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    /*public String getPhotoUrl(){
        return this.photoUrl;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }*/

}
