package com.example.owner.internationalaset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ObjectEvent {
    private String eventName;
    private String eventDate;
    private String eventDes;

    public ObjectEvent(){}
    public ObjectEvent(String name, String date, String des){
        eventName = name;
        eventDate = date;
        eventDes = des;
    }

    public String getEventName(){return eventName;}
    public String getEventDate(){return eventDate;}
    public String getEventDes(){return eventDes;}

    public String eventToString(){
        return eventName + "\n" + eventDate +  "\n" + eventDes;
    }
}
