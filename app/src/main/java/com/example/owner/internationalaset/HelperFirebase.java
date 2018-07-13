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

    public DatabaseReference helperSession(String eventKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions");
    }

    public DatabaseReference helperAgenda(String eventKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Agenda");
    }

    public DatabaseReference helperEventKey(String eventKey){
        return mDatabase.child("Events").child(eventKey);
    }

    public DatabaseReference helperSessionKey(String eventKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey);
    }

    public DatabaseReference helperAgendaKey(String eventKey, String sessionKey, String agendaKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Agenda").child(agendaKey);
    }
}
