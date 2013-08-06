package com.spax.vitebsktransport.domain;

import java.util.Locale;

public class Time implements Comparable<Time> {
    private int hours;
    private int mins;

    public Time() {
    }

    public Time(int hours, int mins) {
        this.hours = hours;
        this.mins = mins;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMins() {
        return mins;
    }

    public void setMins(int mins) {
        this.mins = mins;
    }

    public void addMins(int delta) {
        if (delta > 0) {
            hours += (delta + mins) / 60;
            if (hours == 24) {
                hours = 0;
            }
            mins = (delta + mins) % 60;
        }
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d:%02d", hours, mins);
    }

    @Override
    public int compareTo(Time t) {
        int h1 = hours == 0 ? 24 : hours;
        int h2 = t.hours == 0 ? 24 : t.hours;
        return h1 > h2 ? 1 : h1 < h2 ? -1 : mins > t.mins ? 1 : mins < t.mins ? -1 : 0;
    }

}
