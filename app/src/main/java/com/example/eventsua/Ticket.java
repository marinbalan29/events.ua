package com.example.eventsua;

public class Ticket {

    int ticket_id;
    String ticket_name;
    int cost;

    public Ticket(int ticket_id, String ticket_name, int cost){
        this.ticket_id = ticket_id;
        this.ticket_name = ticket_name;
        this.cost = cost;
    }
    public int getTicket_id() { return ticket_id; }
    public String getTicket_name() { return ticket_name; }
    public int getCost() { return cost; }

    public String getName_Cost() { return ticket_name + cost; }
}
