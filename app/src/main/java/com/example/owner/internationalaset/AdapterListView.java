package com.example.owner.internationalaset;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.EventObject;

public class AdapterListView extends ArrayAdapter {
    ArrayList<?>  objects = null;
    ObjectEvent event = null;
    ObjectAgenda agenda = null;
    ObjectSession session = null;
    ObjectKeynote keynote = null;
    int resource;

    public AdapterListView(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AdapterListView(Context context, int resource, ArrayList<?> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.resource = resource;
    }

    public Object getObject(int position){
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater i = LayoutInflater.from(getContext());
        View view = i.inflate(resource,null);

        if(getObject(position) instanceof ObjectEvent){
            event = (ObjectEvent)getObject(position);
            TextView eventName = (TextView) view.findViewById(R.id.eventName);
            TextView eventStartDate = (TextView) view.findViewById(R.id.eventStartDate);
            TextView eventEndDate = (TextView) view.findViewById(R.id.eventEndDate);
            TextView eventDes = (TextView) view.findViewById(R.id.eventDes);
            TextView eventLocation = (TextView) view.findViewById(R.id.eventLocation);
            ImageView eventImg = (ImageView) view.findViewById(R.id.eventImg);

            setTextView(eventName, event.getEventName());
            setTextView(eventStartDate, event.getEventStartDate());
            setTo(eventEndDate, event.getEventEndDate());
            setTextView(eventDes, event.getEventDes());
            setTextView(eventLocation, event.getEventLocation());

            if (eventImg != null)
                Glide.with(getContext()).load(event.getEventImg()).into(eventImg);
        }

        if(getObject(position) instanceof ObjectAgenda){
            agenda = (ObjectAgenda) getObject(position);
            TextView agendaDate = (TextView) view.findViewById(R.id.agendaDate);
            TextView agendaType = (TextView) view.findViewById(R.id.agendaType);
            TextView agendaName = (TextView) view.findViewById(R.id.agendaName);
            TextView agendaStartTime = (TextView) view.findViewById(R.id.agendaStartTime);
            TextView agendaEndTime = (TextView) view.findViewById(R.id.agendaEndTime);
            TextView agendaDes = (TextView) view.findViewById(R.id.agendaDes);
            setTextView(agendaType, agenda.getAgendaType());
            setTextView(agendaName, agenda.getAgendaName());
            setTextView(agendaStartTime, agenda.getAgendaStartTime());
            setTextView(agendaEndTime, agenda.getAgendaEndTime());
            setTextView(agendaDes, agenda.getAgendaDes());
            setTextView(agendaDate, agenda.getAgendaDate());
        }

        if(getObject(position) instanceof ObjectSession) {
            session = (ObjectSession)getObject(position);
            TextView sessionName = (TextView) view.findViewById(R.id.sessionName);
            TextView sessionPresenter = (TextView) view.findViewById(R.id.sessionPresenter);
            TextView sessionAuthor = (TextView) view.findViewById(R.id.sessionAuthor);
            TextView sessionLocation = (TextView) view.findViewById(R.id.sessionLocation);
            TextView sessionStartTime = (TextView) view.findViewById(R.id.sessionStartTime);
            TextView sessionEndTime = (TextView) view.findViewById(R.id.sessionEndTime);
            setTextView(sessionName, session.getSessionName());
            setPresenter(sessionPresenter, session.getSessionPresenter());
            setAuthor(sessionAuthor, session.getSessionAuthor());
            setTextView(sessionLocation, session.getSessionLocation());
            setTextView(sessionStartTime, session.getSessionStartTime());
            setTextView(sessionEndTime, session.getSessionEndTime());
        }

        if(getObject(position) instanceof ObjectKeynote){
            keynote = (ObjectKeynote) getObject(position);
            TextView keynoteName = (TextView) view.findViewById(R.id.keynoteName);
            TextView keynotePresenter = (TextView) view.findViewById(R.id.keynotePresenter);
            TextView keynoteInstitution = (TextView) view.findViewById(R.id.keynoteInstitution);
            TextView keynoteDes = (TextView) view.findViewById(R.id.keynoteDes);
            TextView keynoteStartTime = (TextView) view.findViewById(R.id.keynoteStartTime);
            TextView keynoteEndTime = (TextView) view.findViewById(R.id.keynoteEndTime);
            ImageView keynoteImg = (ImageView) view.findViewById(R.id.keynoteImge);
            setTextView(keynoteName, keynote.getKeynoteName());
            setTextView(keynotePresenter, keynote.getKeynotePresenter());
            setTextView(keynoteInstitution, keynote.getKeynoteInstitution());
            setTextView(keynoteDes, keynote.getKeynoteDes());
            setTextView(keynoteStartTime, keynote.getKeynoteStartTime());
            setTo(keynoteEndTime, keynote.getKeynoteEndTime());
            if (keynoteImg != null)
                Glide.with(getContext()).load(keynote.getKeynoteImg()).into(keynoteImg);
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

    public void setPresenter(TextView text, String string){
        if(text != null){
            text.setText("");
            if(!string.equals("")){
                text.setText("Presenter: " + string);
            }
        }
    }

    public void setAuthor(TextView text, String string){
        if(text != null){
            text.setText("");
            if(!string.equals("")){
                text.setText("Author: " + string);
            }
        }
    }

}
