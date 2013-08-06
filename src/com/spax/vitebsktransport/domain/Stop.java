package com.spax.vitebsktransport.domain;

public class Stop extends DBObject {
    private String name;
    private int latitudeE6;
    private int longitudeE6;

    public Stop() {
    }

    public Stop(String name, int latitudeE6, int longitudeE6) {
        this.name = name;
        this.latitudeE6 = latitudeE6;
        this.longitudeE6 = longitudeE6;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLatitudeE6() {
        return latitudeE6;
    }

    public void setLatitudeE6(int latitudeE6) {
        this.latitudeE6 = latitudeE6;
    }

    public int getLongitudeE6() {
        return longitudeE6;
    }

    public void setLongitudeE6(int longitudeE6) {
        this.longitudeE6 = longitudeE6;
    }

    @Override
    public String toString() {
        return name;
    }

}
