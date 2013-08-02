package by.sands.vitebsktransport.domain;

public enum TransportType {

    /**
     * Городской автобус.
     */
    CITY_BUS("CITY_BUS"),

    /**
     * Дачный автобус.
     */
    HOLIDAY_BUS("HOLIDAY_BUS"),

    /**
     * Трамвай.
     */
    TRAM("TRAM"),

    /**
     * Троллейбус.
     */
    TROLLEYBUS("TROLLEYBUS");

    private String id;

    private TransportType(String type) {
        this.id = type;
    }

    public String getId() {
        return id;
    }
}
