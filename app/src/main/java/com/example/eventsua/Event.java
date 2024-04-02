package com.example.eventsua;

import java.util.Calendar;

public class Event  {

    int event_id;
    String event_name;
    String event_place;
    Calendar event_date;
    String image_url;
    String ticket_name;
    String event_description;



    public Event(int event_id, String event_name, String event_place, Calendar event_date, String image_url) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_place = event_place;
        this.event_date = event_date;
        this.image_url = image_url;
    }
    public Event(String event_name, String event_place, Calendar event_date, String image_url, String ticket_name) {
        this.event_name = event_name;
        this.event_place = event_place;
        this.event_date = event_date;
        this.image_url = image_url;
        this.ticket_name = ticket_name;
    }
    public Event(String event_name, String event_place,  String image_url, String event_description, Calendar event_date) {
        this.event_name = event_name;
        this.event_place = event_place;
        this.event_date = event_date;
        this.image_url = image_url;
        this.event_description = event_description;
    }
    public int getEvent_id() { return event_id; }
    public String getEvent_name() { return event_name; }
    public String getEvent_place() { return event_place; }
    public String getImage_url() { return image_url; }
    public Calendar getEvent_date() { return event_date; }

    public String getTicket_name() { return ticket_name; }
    public String getEvent_description() { return event_description; }


    public String toString() { return event_name+event_id+event_place+image_url; }

}
