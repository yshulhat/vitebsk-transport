package by.sands.vitebsktransport.domain;

import java.util.Locale;

public class Time {
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
        hours += (delta + mins) / 60;
        mins = (delta + mins) % 60;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d:%02d", hours, mins);
    }

}
