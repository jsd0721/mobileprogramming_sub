package com.example.clanner;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

/* 게시자의 uid를 추가로 저장해서 찾아봄 => publisher */

public class WriteInfo {

    private String title;
    private ArrayList<String> content;
    private String publisher;
    private Date createdAt;

    public WriteInfo(String title, ArrayList<String> content, String publisher, Date createdAt){
        this.title = title;
        this.content=content;
        this.publisher=publisher;
        this.createdAt=createdAt;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public ArrayList<String> getContent(){
        return this.content;
    }
    public void setContent(ArrayList<String> content){
        this.content = content;
    }

    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public Date getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }

}
