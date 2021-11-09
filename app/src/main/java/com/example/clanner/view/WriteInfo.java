package com.example.clanner.view;

public class WriteInfo {
    private  String title;
    private  String content;

    public WriteInfo(String title, String content){
        this.title = title;
        this.content = content;
    }

    public  String getTitle() { return this.title; }
    public void  setTitle(String title) {this.title = title; }
    public  String getContent() { return this.content; }
    public void  setContent(String title) {this.content = content; }
}
