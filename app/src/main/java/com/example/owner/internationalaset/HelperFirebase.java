package com.example.owner.internationalaset;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelperFirebase {
    private DatabaseReference mDatabase;

    public HelperFirebase(){
        mDatabase= FirebaseDatabase.getInstance().getReference();
    }

    //event
    public DatabaseReference helperEvent(){
        return mDatabase.child("Events");
    }

    public DatabaseReference helperEventKey(String eventKey){
        return mDatabase.child("Events").child(eventKey);
    }

    //session
    public DatabaseReference helperSession(String eventKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions");
    }

    public DatabaseReference helperSessionKey(String eventKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey);
    }

    //article
    public DatabaseReference helperArticle(String eventKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Article");
    }

    public DatabaseReference helperArticleKey(String eventKey, String sessionKey, String articleKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Article").child(articleKey);
    }

    //keynote
    public DatabaseReference helperKeynote(String eventKey, String sessionKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Keynote");
    }

    public DatabaseReference helperKeynoteKey(String eventKey, String sessionKey, String keynoteKey){
        return mDatabase.child("Events").child(eventKey).child("Sessions").child(sessionKey).child("Keynote").child(keynoteKey);
    }

    //passcode
    public DatabaseReference helperPasscode(){
        return mDatabase.child("Passcode");
    }

    public DatabaseReference helperPasscodeKey(String passcode){
        return mDatabase.child("Passcode").child(passcode);
    }

    //user
    public DatabaseReference helperUser() { return mDatabase.child("User"); }

    public DatabaseReference helperUserKey(String userKey) { return mDatabase.child("User").child(userKey); }
}
