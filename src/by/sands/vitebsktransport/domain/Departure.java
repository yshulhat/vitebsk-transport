package by.sands.vitebsktransport.domain;

public class Departure extends DBObject {
    private String day;
    private String time;
    private long fromStopId;
    private long toStopId;
    private long directionId;

    public Departure() {
    }

    public Departure(String day, String time, long fromStopId, long toStopId, long directionId) {
        this.day = day;
        this.time = time;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.directionId = directionId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getFromStopId() {
        return fromStopId;
    }

    public void setFromStopId(long fromStopId) {
        this.fromStopId = fromStopId;
    }

    public long getToStopId() {
        return toStopId;
    }

    public void setToStopId(long toStopId) {
        this.toStopId = toStopId;
    }

    public long getDirectionId() {
        return directionId;
    }

    public void setDirectionId(long directionId) {
        this.directionId = directionId;
    }

}
