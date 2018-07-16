package com.example.owner.internationalaset;

import java.util.HashMap;
import java.util.Map;

public class ObjectAgenda{
    private String agendaType;
    private String agendaName;
    private String agendaStartTime;
    private String agendaEndTime;
    private String agendaDes;

    public ObjectAgenda(){}
    public ObjectAgenda(String type, String name, String start, String end, String des){
        agendaType = type;
        agendaName = name;
        agendaStartTime = start;
        agendaEndTime = end;
        agendaDes = des;
    }

    public String getAgendaType() {return agendaType;}
    public String getAgendaName() {return agendaName;}
    public String getAgendaStartTime() {return agendaStartTime;}
    public String getAgendaEndTime() {return agendaEndTime;}
    public String getAgendaDes() {return agendaDes;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("agendaType",agendaType);
        hashMap.put("agendaName",agendaName);
        hashMap.put("agendaStartTime",agendaStartTime);
        hashMap.put("agendaEndTime",agendaEndTime);
        hashMap.put("agendaDes",agendaDes);
        return hashMap;
    }

    public String agendaToString(){
        return agendaType  + "\n" + agendaName + "\n" + agendaStartTime + "\n" + agendaEndTime + "\n" + agendaDes;
    }
}
