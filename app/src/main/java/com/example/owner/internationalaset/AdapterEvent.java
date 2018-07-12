package com.example.owner.internationalaset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterEvent extends ArrayAdapter<ObjectEvent> {
    ArrayList<ObjectEvent>  events;
    int resource;

    public AdapterEvent(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdapterEvent(Context context, int resource, ArrayList<ObjectEvent> events) {
        super(context, resource, events);
        this.events = events;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater i = LayoutInflater.from(getContext());
        View view = i.inflate(resource,null);

        TextView name = (TextView) view.findViewById(R.id.eventName);
        TextView startDate = (TextView) view.findViewById(R.id.eventStartDate);
        TextView endDate = (TextView) view.findViewById(R.id.eventEndDate);
        TextView des = (TextView) view.findViewById(R.id.eventDes);
        TextView location = (TextView) view.findViewById(R.id.eventLocation);

        ObjectEvent e = events.get(position);
        setTextView(name, e.getEventName());
        setTextView(startDate, e.getEventStartDate());
        setTo(endDate, e.getEventEndDate());
        setTextView(des, e.getEventDes());
        setTextView(location, e.getEventLocation());

        return view;
    }

    public void setTextView(TextView text, String string){
        if(text != null){
            text.setText("");
            if(string != null){
                text.setText(string);
            }
        }
    }

    public void setTo(TextView text, String string){
        if(text != null){
            text.setText("");
            if(string != null){
                text.setText(" To " + string);
            }
        }
    }
}
