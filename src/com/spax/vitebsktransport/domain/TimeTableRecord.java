package com.spax.vitebsktransport.domain;

import java.util.ArrayList;
import java.util.List;

public class TimeTableRecord {
    private List<Time> times = new ArrayList<Time>();

    public void addTime(Time time) {
        times.add(time);
    }

    public List<Time> getTimes() {
        return times;
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @Override
    public String toString() {
        if (times.isEmpty()) {
            return "";
        } else {
            StringBuffer result = new StringBuffer(String.format("%02d: ",times.get(0).getHours()));
            for (Time t : times) {
                result.append(String.format(" %02d",t.getMins()));
            }
            return result.toString();
        }
    }
}
