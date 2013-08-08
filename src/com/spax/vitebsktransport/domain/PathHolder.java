package com.spax.vitebsktransport.domain;

public class PathHolder {
    private static final PathHolder instance;
    private long fromStop;
    private long toStop;

    static {
        instance = new PathHolder();
    }

    public static PathHolder getInstance() {
        return instance;
    }

    public long getFromStop() {
        return fromStop;
    }

    public void setFromStop(long fromStop) {
        this.fromStop = fromStop;
    }

    public long getToStop() {
        return toStop;
    }

    public void setToStop(long toStop) {
        this.toStop = toStop;
    }
}
