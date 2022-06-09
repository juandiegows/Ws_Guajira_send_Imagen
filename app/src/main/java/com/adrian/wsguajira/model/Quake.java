package com.adrian.wsguajira.model;


public class Quake {

    private Properties  properties;
    private String id;

    public Quake() {
    }

    public Quake(Properties properties, String id) {
        this.properties = properties;
        this.id = id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
