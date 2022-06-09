package com.adrian.wsguajira.model;

import java.util.List;

public class Feature {
    private List<Quake> features;

    public Feature() {
    }

    public Feature(List<Quake> features) {
        this.features = features;
    }

    public List<Quake> getFeatures() {
        return features;
    }

    public void setFeatures(List<Quake> features) {
        this.features = features;
    }
}
