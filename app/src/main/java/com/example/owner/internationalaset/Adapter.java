package com.example.owner.internationalaset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Adapter extends ArrayAdapter {
    ArrayList<?>  objects = null;
    ArrayList<ObjectEvent>  event = null;
    ArrayList<ObjectAgenda>  agenda = null;
    ArrayList<ObjectSession>  session = null;
    int resource;

    public Adapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public Adapter(Context context, int resource, ArrayList<?> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater i = LayoutInflater.from(getContext());
        View view = i.inflate(resource,null);

        if(objects.get(0) instanceof ObjectEvent) event = (ArrayList<ObjectEvent>) objects;
        if(objects.get(0) instanceof ObjectAgenda) agenda = (ArrayList<ObjectAgenda>) objects;
        if(objects.get(0) instanceof ObjectSession) session = (ArrayList<ObjectSession>) objects;

        //event
        if(event != null) {
            ObjectEvent e = event.get(position);

            TextView eventName = (TextView) view.findViewById(R.id.eventName);
            TextView eventStartDate = (TextView) view.findViewById(R.id.eventStartDate);
            TextView eventEndDate = (TextView) view.findViewById(R.id.eventEndDate);
            TextView eventDes = (TextView) view.findViewById(R.id.eventDes);
            TextView eventLocation = (TextView) view.findViewById(R.id.eventLocation);
            ImageView eventImg = (ImageView) view.findViewById(R.id.eventImg);

            setTextView(eventName, e.getEventName());
            setTextView(eventStartDate, e.getEventStartDate());
            setTo(eventEndDate, e.getEventEndDate());
            setTextView(eventDes, e.getEventDes());
            setTextView(eventLocation, e.getEventLocation());

            if (eventImg != null)
                Glide.with(getContext()).load(e.getEventImg()).into(eventImg);
        }

        //agenda
        if(agenda != null) {
            ObjectAgenda a = agenda.get(position);

            TextView agendaDate = (TextView) view.findViewById(R.id.agendaDate);
            TextView agendaType = (TextView) view.findViewById(R.id.agendaType);
            TextView agendaName = (TextView) view.findViewById(R.id.agendaName);
            TextView agendaStartTime = (TextView) view.findViewById(R.id.agendaStartTime);
            TextView agendaEndTime = (TextView) view.findViewById(R.id.agendaEndTime);
            TextView agendaDes = (TextView) view.findViewById(R.id.agendaDes);

            setTextView(agendaType, a.getAgendaType());
            setTextView(agendaName, a.getAgendaName());
            setTextView(agendaStartTime, a.getAgendaStartTime());
            setTextView(agendaEndTime, a.getAgendaEndTime());
            setTextView(agendaDes, a.getAgendaDes());
            setTextView(agendaDate, a.getAgendaDate());
        }

        //session
        if(session != null) {
            ObjectSession s = session.get(position);

            TextView sessionName = (TextView) view.findViewById(R.id.sessionName);
            TextView sessionInstitution = (TextView) view.findViewById(R.id.sessionInstitution);
            TextView sessionLocation = (TextView) view.findViewById(R.id.sessionLocation);
            TextView sessionStartTime = (TextView) view.findViewById(R.id.sessionStartTime);
            TextView sessionEndTime = (TextView) view.findViewById(R.id.sessionEndTime);
            TextView sessionPeople = (TextView) view.findViewById(R.id.sessionPeople);

            setTextView(sessionName, s.getSessionName());
            setTextView(sessionInstitution, s.getSessionInstitution());
            setTextView(sessionLocation, s.getSessionLocation());
            setTextView(sessionStartTime, s.getSessionStartTime());
            setTextView(sessionEndTime, s.getSessionEndTime());
            setTextView(sessionPeople, s.getSessionPeople());
        }
        return view;
    }

    public void setTextView(TextView text, String string){
        if(text != null){
            text.setText("");
            if(!string.equals("")){
                text.setText(string);
            }
        }
    }

    public void setTo(TextView text, String string){
        if(text != null){
            text.setText("");
            if(!string.equals("")){
                text.setText(" To " + string);
            }
        }
    }
}
