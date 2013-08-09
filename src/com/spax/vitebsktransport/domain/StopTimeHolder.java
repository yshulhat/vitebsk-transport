package com.spax.vitebsktransport.domain;

public class StopTimeHolder {
    private Stop stop;
    private Time time;

    public StopTimeHolder() {
    }

    public StopTimeHolder(Stop stop, Time time) {
        this.stop = stop;
        this.time = time;
    }

    public Stop getStop() {
        return stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return time.toString() + "\t" + stop.getName();
    }

}
