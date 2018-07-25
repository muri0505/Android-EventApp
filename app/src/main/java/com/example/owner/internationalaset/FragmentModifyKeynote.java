package com.example.owner.internationalaset;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/*
    FragmentModifyKeynote: get eventKey&sessionKey&keynoteKey from ActivityControlKeynote
    check valid keynoteKey and get keynote from firebase or create new keynoteKey and new keynote
    Edit button to push update/new keynote, cancel button back to ActivityControlKeynote
 */
public class FragmentModifyKeynote extends HelperDateTime {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectKeynote keynote;
    private String getEventKey = null;
    private String getSessionKey = null;
    private String getKeynoteKey = null;

    private TextView mode;
    private EditText keynoteName;
    private EditText keynotePresenter;
    private EditText keynoteInstitution;
    private EditText keynoteDes;
    private EditText keynoteStartTime;
    private EditText keynoteEndTime;
    private String keynoteImg = "";
    private String emptyName;
    private static final String TAG = "FragmentModifyKeynote";

    public FragmentModifyKeynote(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_keynote, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        keynoteName= (EditText) view.findViewById(R.id.keynoteName);
        keynotePresenter= (EditText) view.findViewById(R.id.keynotePresenter);
        keynoteInstitution= (EditText) view.findViewById(R.id.keynoteInstitution);
        keynoteDes = (EditText) view.findViewById(R.id.keynoteDes);
        keynoteStartTime = (EditText) view.findViewById(R.id.keynoteStartTime);
        keynoteEndTime = (EditText) view.findViewById(R.id.keynoteEndTime);

        //input keyboard format
        keynoteStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {setTime(keynoteStartTime);    }
        });
        keynoteEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(keynoteEndTime);
            }
        });

        //get eventKey&sessionKey&keynoteKey from ActivityControlKeynote
        getEventKey = getArguments().getString("eventKey");
        getSessionKey = getArguments().getString("sessionKey");
        getKeynoteKey = getArguments().getString("keynoteKey");
        Log.i(TAG,"Get eventKey, sessionKey and keynoteKey from ActivityControlSession " + getEventKey + ", " + getSessionKey + ", " + getKeynoteKey);

        //check valid keynoteKey to get keynote
        if(getKeynoteKey != null) {
            Log.i(TAG,"Mode: Edit Keynote");
            helperFirebase.helperKeynote(getEventKey,getSessionKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getKeynote(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Keynote");
            mode.setText("Create Keynote");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current keynote
                String name, presenter, institution, des, startTime, endTime;
                name = keynoteName.getText().toString();
                presenter = keynotePresenter.getText().toString();
                institution = keynoteInstitution.getText().toString();
                des = keynoteDes.getText().toString();
                startTime = keynoteStartTime.getText().toString();
                endTime = keynoteEndTime.getText().toString();
                keynote = new ObjectKeynote(name, presenter, institution, des, startTime, endTime, keynoteImg);

                //keynote validation
                if (empty(name, "Keynote Name") || empty(presenter, "Keynote Presenter") || empty(institution, "Keynote Institution")
                        || empty(des, "Keynote Description") || empty(startTime, "Keynote StartTime") ) {
                    new AlertDialog.Builder(getActivity())
                            .setMessage(emptyName + " cannot be empty")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                } else {
                    //create new keynoteKey and keynote or update keynote under exist keynoteKey
                    if (getKeynoteKey == null) {
                        getKeynoteKey = helperFirebase.helperKeynote(getEventKey, getSessionKey).push().getKey();
                        helperFirebase.helperKeynoteKey(getEventKey, getSessionKey, getKeynoteKey).setValue(keynote);
                        Log.i(TAG, "New keynoteKey and keynote created. keynoteKey: " + getKeynoteKey);
                    } else {
                        helperFirebase.helperKeynoteKey(getEventKey, getSessionKey, getKeynoteKey).updateChildren(keynote.toHashMap());
                        Log.i(TAG, "keynote updated. keynoteKey: " + getKeynoteKey);
                    }
                    backToControl();
                }
            }
        });

        //cancel button back to ActivityControlKeynote
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get keynote from firebase with keynoteKey and show in editText
    public void getKeynote(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getKeynoteKey)) {
                mode.setText("Edit Keynote");
                ObjectKeynote a = (ObjectKeynote) data.getValue(ObjectKeynote.class);
                keynoteName.setText(a.getKeynoteName());
                keynotePresenter.setText(a.getKeynotePresenter());
                keynoteInstitution.setText(a.getKeynoteInstitution());
                keynoteDes.setText(a.getKeynoteDes());
                keynoteStartTime.setText(a.getKeynoteStartTime());
                keynoteEndTime.setText(a.getKeynoteEndTime());
                keynoteImg = a.getKeynoteImg();
                break;
            }
        }
    }

    //Intent to ActivityControlKeynote, default showing keynote list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlKeynote.class);
        i.putExtra("eventKey",getEventKey);
        i.putExtra("sessionKey",getSessionKey);
        startActivity(i);
    }

    //check object empty attribute
    public boolean empty(String text, String name){
        emptyName = name;
        return text.equals(null)||text.equals("");
    }
}