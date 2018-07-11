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

    public AdapterEvent(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdapterEvent(Context context, int resource, ArrayList<ObjectEvent> events) {
        super(context, resource, events);
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater i = LayoutInflater.from(getContext());
        View view = i.inflate(R.layout.layout_list_event,null);

        TextView name = (TextView) view.findViewById(R.id.eventName);
        TextView date = (TextView) view.findViewById(R.id.eventDate);
        TextView des = (TextView) view.findViewById(R.id.eventDes);

        ObjectEvent e = events.get(position);
        name.setText(e.getEventName());
        date.setText(e.getEventDate());
        des.setText(e.getEventDes());
        return view;
    }
}
