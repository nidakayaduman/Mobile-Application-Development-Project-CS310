package com.myproject.myapplication;

import java.io.Serializable;

public class Event implements Serializable {
    private String id;
    private String name;
    private String date;
    private String time;
    private String location;
    private String description;

    // Constructor
    public Event(String id, String name, String date, String time, String location, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
