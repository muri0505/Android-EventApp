package com.example.owner.internationalaset;

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

    public String agendaToString(){
        return agendaName + "\n" + agendaInstitution + "\n" + agendaLocation + "\n" +
                agendaStartTime + "\n" + agendaEndTime + "\n" + agendaPeople;}
}
