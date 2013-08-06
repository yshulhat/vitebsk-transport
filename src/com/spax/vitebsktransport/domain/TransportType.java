package com.spax.vitebsktransport.domain;

public enum TransportType {

    /**
     * Городской автобус.
     */
    CITY_BUS("CITY_BUS", "Городские автобусы"),

    /**
     * Дачный автобус.
     */
    HOLIDAY_BUS("HOLIDAY_BUS", "Дачные автобусы"),

    /**
     * Трамвай.
     */
    TRAM("TRAM", "Трамваи"),

    /**
     * Троллейбус.
     */
    TROLLEYBUS("TROLLEYBUS", "Троллейбусы");

    private String id;
    private String title;

    private TransportType(String type, String title) {
        this.id = type;
        this.title = title;
    }

    public String getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
}
