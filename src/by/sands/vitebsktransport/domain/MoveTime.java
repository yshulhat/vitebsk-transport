package by.sands.vitebsktransport.domain;

public class MoveTime extends DBObject {
    private int time;
    private long fromStopId;
    private long toStopId;
    private long directionId;

    public MoveTime() {
    }

    public MoveTime(int time, long fromStopId, long toStopId, long directionId) {
        this.time = time;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.directionId = directionId;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
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

    @Override
    public String toString() {
        return
            "Moves " + time + " min from [" + fromStopId + "] to [" + toStopId + "] in [" + directionId + "] direction";
    }

}
