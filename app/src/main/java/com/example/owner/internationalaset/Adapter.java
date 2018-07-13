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

public class Adapter extends ArrayAdapter<ObjectEvent> {
    ArrayList<ObjectEvent>  events;
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
