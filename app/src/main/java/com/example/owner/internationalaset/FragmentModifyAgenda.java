package com.example.owner.internationalaset;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    FragmentModifyAgenda: get eventKey&agendaKey from ActivityControlAgenda
    check valid agendaKey and get agenda from firebase or create new agendaKey and new agenda
    Edit button to push update/new agenda, cancel button back to ActivityControlAgenda
 */
public class FragmentModifyAgenda extends Fragment {
    private HelperFirebase helperFirebase = new HelperFirebase();
    private ObjectAgenda agenda;
    private String getEventKey = null;
    private String getAgendaKey = null;

    private TextView mode;
    private EditText agendaType;
    private EditText agendaName;
    private EditText agendaStartTime;
    private EditText agendaEndTime;
    private EditText agendaDes;
    private static final String TAG = "FragmentModifyAgenda";

    public FragmentModifyAgenda(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_agenda, container, false);

        mode = (TextView) view.findViewById(R.id.mode);
        agendaType= (EditText) view.findViewById(R.id.agendaType);
        agendaName = (EditText) view.findViewById(R.id.agendaName);
        agendaStartTime = (EditText) view.findViewById(R.id.agendaStartTime);
        agendaEndTime = (EditText) view.findViewById(R.id.agendaEndTime);
        agendaDes = (EditText) view.findViewById(R.id.agendaDes);

        //get eventKey&agendaKey from ActivityControlAgenda
        getEventKey = getArguments().getString("eventKey");
        getAgendaKey = getArguments().getString("agendaKey");
        Log.i(TAG,"Get eventKey and agendaKey from ActivityControlAgenda " + getEventKey + ", " + getAgendaKey);

        //check valid agendaKey to get agenda
        if(getAgendaKey != null) {
            Log.i(TAG,"Mode: Edit Agenda");
            helperFirebase.helperAgenda(getEventKey).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    getAgenda(dataSnapshot);
                }
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }else{
            Log.i(TAG,"Mode: Create Agenda");
            mode.setText("Create Agenda");
        }

        Button edit = (Button) view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //edit button to create current agenda
                String type, name,startTime, endTime, des;
                type = agendaType.getText().toString();
                name = agendaName.getText().toString();
                startTime = agendaStartTime.getText().toString();
                endTime = agendaEndTime.getText().toString();
                des = agendaDes.getText().toString();
                agenda = new ObjectAgenda(type, name, startTime, endTime, des);

                //create new agendaKey and agenda or update agenda under exist agendaKey
                if(getAgendaKey==null) {
                    getAgendaKey = helperFirebase.helperAgenda(getEventKey).push().getKey();
                    helperFirebase.helperAgendaKey(getEventKey, getAgendaKey).setValue(agenda);
                    Log.i(TAG, "New agendaKey and agenda created. agendaKey: " + getAgendaKey);
                }else{
                    helperFirebase.helperAgendaKey(getEventKey, getAgendaKey).updateChildren(agenda.toHashMap());
                    Log.i(TAG, "agenda updated. agendaKey: " + getAgendaKey);
                }
                backToControl();
            }
        });

        //cancel button back to ActivityControlAgenda
        Button cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backToControl();
            }
        });
        return view;
    }

    //get agenda from firebase with agendaKey and show in editText
    public void getAgenda(DataSnapshot dataSnapshot){
        for(DataSnapshot data : dataSnapshot.getChildren()){
            String key = data.getKey();

            if(key.equals(getAgendaKey)) {
                mode.setText("Edit Agenda");
                ObjectAgenda s = (ObjectAgenda) data.getValue(ObjectAgenda.class);
                agendaType.setText(s.getAgendaType());
                agendaName.setText(s.getAgendaName());
                agendaStartTime.setText(s.getAgendaStartTime());
                agendaEndTime.setText(s.getAgendaEndTime());
                agendaDes.setText(s.getAgendaDes());
                break;
            }
        }
    }

    //Intent to ActivityControlAgenda, default showing agenda list
    public void backToControl(){
        Intent i = new Intent(getActivity(), ActivityControlAgenda.class);
        i.putExtra("eventKey",getEventKey);
        startActivity(i);
        Log.i(TAG, "Intent to ActivityControlAgenda");
    }
}