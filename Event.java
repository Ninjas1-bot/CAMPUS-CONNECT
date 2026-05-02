package com.example.campusconnect.model;

import java.util.HashSet;
import java.util.Set;

public class Event {
    private int id;
    private String title;
    private String date;
    public String description;
    private Set<Integer> attendees = new HashSet<>();

    public Event() {}

    public Event(int id, String title, String description, String date) {
        // constructor that accepts id, title, description and date
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    // simpler constructor used in controller
    public Event(int id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Set<Integer> getAttendees() { return attendees; }
    public void setAttendees(Set<Integer> attendees) { this.attendees = attendees; }
}
