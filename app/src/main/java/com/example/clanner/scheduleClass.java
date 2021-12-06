package com.example.clanner;


import java.util.HashMap;
import java.util.Map;

public class scheduleClass {

    String user;
    String content;
    int alarmState;
    String time;
    String Date;

    //내부 아이템들 초기화 시키기 위한 생성자
    public scheduleClass(String user, String content, int alarmState, String time, String Date){
        this.user = user;
        this.content = content;
        this.alarmState = alarmState;
        this.time = time;
        this.Date = Date;
    }

    //객체만 만들기 위한 생성자
    public scheduleClass(){

    }

    //getter 메서드 선언
    public String getContent(){ return content; }
    public int getAlarmState(){ return alarmState;}
    public String getTime(){return time;}
    public String getUser() { return user; }
    public String getDate(){ return Date;}

    //setter 메서드 선언

    public void setContent(String Content){ this.content = Content; }
    public void setAlarmState(int alarmState){ this.alarmState = alarmState; }
    public void setTime(String time){this.time = time;}
    public void setUser(String user) { this.user = user; }
    public void setDate(String Date) { this.Date = Date; }

    public Map<Object, Object> toMap() {
        HashMap<Object, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("content", content);
        result.put("alarmState", alarmState);
        result.put("time", time);
        result.put("date",Date);

        return result;
    }


}
