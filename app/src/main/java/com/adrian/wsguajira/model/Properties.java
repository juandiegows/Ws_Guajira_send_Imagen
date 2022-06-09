package com.adrian.wsguajira.model;

public class Properties {

    private double mag;
    private String place, type, title;

    public Properties() {
    }

    public Properties(double mag, String place, String type, String title) {
        this.mag = mag;
        this.place = place;
        this.type = type;
        this.title = title;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
