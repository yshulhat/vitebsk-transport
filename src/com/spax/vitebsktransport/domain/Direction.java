package com.spax.vitebsktransport.domain;

public class Direction extends DBObject {
    private String name;
    private long routeId;

    public Direction() {
    }

    public Direction(String name, long routeId) {
        this.name = name;
        this.routeId = routeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return name;
    }

}
