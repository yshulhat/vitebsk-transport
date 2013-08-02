package by.sands.vitebsktransport.domain;

public enum TransportType {

    /**
     * ��������� �������.
     */
    CITY_BUS("CITY_BUS"),

    /**
     * ������ �������.
     */
    HOLIDAY_BUS("HOLIDAY_BUS"),

    /**
     * �������.
     */
    TRAM("TRAM"),

    /**
     * ����������.
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
