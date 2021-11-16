package com.example.clanner;


public class scheduleClass {

    String user;
    String content;
    int alarmState;
    String time;





    public scheduleClass(String user, String content, int alarmState, String time){
        this.user = user;
        this.content = content;
        this.alarmState = alarmState;
        this.time = time;
    }

    //getter 메서드 선언
    public String getContent(){ return content; }
    public int getAlarmState(){ return alarmState;}
    public String getTime(){return time;}
    public String getUser() { return user; }

    //setter 메서드 선언

    public void setContent(String Content){ this.content = Content; }
    public void setAlarmState(int alarmState){ this.alarmState = alarmState; }
    public void setTime(String time){this.time = time;}
    public void setUser(String user) { this.user = user; }


}
