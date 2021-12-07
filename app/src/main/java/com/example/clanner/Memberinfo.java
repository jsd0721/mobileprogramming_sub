package com.example.clanner;

import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class Memberinfo {

    private String name;
    private String photoUrl;
    private String email;





    public Memberinfo(String name, String photoUrl, String email){
        this.name = name;
        this.photoUrl=photoUrl;
        this.email = email;
    }

    public Memberinfo(String name) {
        this.name = name;
    }


    //getter
    public String getName(){
        return this.name;
    }
    public String getPhotoUrl(){
        return this.photoUrl;
    }
    public String getEmail() { return email; }

    //setter
    public void setName(String name){
        this.name = name;
    }
    public void setPhotoUrl(String photoUrl){
        this.photoUrl = photoUrl;
    }
    public void setEmail(String email) { this.email = email; }

    //데이터를 집어넣기 용이하게 hashmap 형태로 바꾸는 메서드
    Map<String, String> tomap(){

        Map<String,String> dataMap = new HashMap<>();

        dataMap.put("user_email",email);
        dataMap.put("user_nickname",name);
        dataMap.put("user_photo",photoUrl);

        return dataMap;
    }

}
