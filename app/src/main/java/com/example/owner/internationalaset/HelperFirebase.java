package com.example.owner.internationalaset;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelperFirebase {
    private DatabaseReference mDatabase;

    public HelperFirebase(){
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference helperEvent(){
        return mDatabase.child("Events");
    }

    public DatabaseReference helperAgenda(String eventKey){
        return mDatabase.child("Events").child(eventKey).child("Agendas");
    }

    public DatabaseReference helperSession(String eventKey, String agendaKey){
        return mDatabase.child("Events").child(eventKey).child("Agendas").child(agendaKey).child("Session");
    }

    public DatabaseReference helperEventKey(String eventKey){
        return mDatabase.child("Events").child(eventKey);
    }

    public DatabaseReference helperAgendaKey(String eventKey, String agendaKey){
        return mDatabase.child("Events").child(eventKey).child("Agendas").child(agendaKey);
    }

    public DatabaseReference helperSessionKey(String eventKey, String agendaKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Agendas").child(agendaKey).child("Session").child(sessionKey);
    }
}
