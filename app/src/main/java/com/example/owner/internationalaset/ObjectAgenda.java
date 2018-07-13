package com.example.owner.internationalaset;

import java.util.HashMap;

public class ObjectAgenda {
    private String agendaName;
    private String agendaInstitution;
    private String agendaLocation;
    private String agendaStartTime;
    private String agendaEndTime;
    private String agendaPeople;

    public ObjectAgenda(){}

    public ObjectAgenda(String n, String i, String l, String s, String e, String p){
        agendaName = n;
        agendaInstitution = i;
        agendaLocation = l;
        agendaStartTime = s;
        agendaEndTime = e;
        agendaPeople = p;
    }

    public String getAgendaName() {return agendaName;}
    public String getAgendaInstitution() {return agendaInstitution;}
    public String getAgendaLocation() {return agendaLocation;}
    public String getAgendaStartTime() {return agendaStartTime;}
    public String getAgendaEndTime() {return agendaEndTime;}
    public String getAgendaPeople() {return agendaPeople;}

    public HashMap<String,Object> toHashMap(){
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("agendaName",agendaName);
        hashMap.put("agendaInstitution",agendaInstitution);
        hashMap.put("agendaLocation",agendaLocation);
        hashMap.put("agendaStartTime",agendaStartTime);
        hashMap.put("agendaEndTime",agendaEndTime);
        hashMap.put("agendaPeople",agendaPeople);
        return hashMap;
    }

    public String agendaToString(){
        return agendaName + "\n" + agendaInstitution + "\n" + agendaLocation + "\n" +
                agendaStartTime + "\n" + agendaEndTime + "\n" + agendaPeople;}
}
