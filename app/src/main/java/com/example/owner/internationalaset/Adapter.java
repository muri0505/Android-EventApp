package com.example.owner.internationalaset;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {
    ArrayList<ObjectEvent>  events;
    ArrayList<ObjectAgenda>  agenda;
    int resource;

    public Adapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public Adapter(Context context, int resource, ArrayList<ObjectEvent> events) {
        super(context, resource, events);
        this.events = events;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater i = LayoutInflater.from(getContext());
        View view = i.inflate(resource,null);

        //event
        TextView eventName = (TextView) view.findViewById(R.id.eventName);
        TextView eventStartDate = (TextView) view.findViewById(R.id.eventStartDate);
        TextView eventEndDate = (TextView) view.findViewById(R.id.eventEndDate);
        TextView eventDes = (TextView) view.findViewById(R.id.eventDes);
        TextView eventLocation = (TextView) view.findViewById(R.id.eventLocation);
        ImageView eventImg = (ImageView)view.findViewById(R.id.eventImg);

        ObjectEvent e = events.get(position);
        setTextView(eventName, e.getEventName());
        setTextView(eventStartDate, e.getEventStartDate());
        setTo(eventEndDate, e.getEventEndDate());
        setTextView(eventDes, e.getEventDes());
        setTextView(eventLocation, e.getEventLocation());

        if(eventImg != null)
            Glide.with(getContext()).load(e.getEventImg()).into(eventImg);
/*
        //agenda
        TextView agendaType = (TextView) view.findViewById(R.id.agendaType);
        TextView agendaName = (TextView) view.findViewById(R.id.agendaName);
        TextView agendaStartTime = (TextView) view.findViewById(R.id.agendaStartTime);
        TextView agendaEndTime = (TextView) view.findViewById(R.id.agendaEndTime);
        TextView agendaDes = (TextView) view.findViewById(R.id.agendaDes);

        ObjectAgenda a = agenda.get(position);
        setTextView(agendaType, a.getAgendaType());
        setTextView(agendaName, a.getAgendaName());
        setTextView(agendaStartTime, a.getAgendaStartTime());
        setTextView(agendaEndTime, a.getAgendaEndTime());
        setTextView(agendaDes, a.getAgendaDes());
*/
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
