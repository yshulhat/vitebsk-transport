package com.spax.vitebsktransport.domain;

public enum TransportType {

    /**
     * ��������� �������.
     */
    CITY_BUS("CITY_BUS", "��������� ��������"),

    /**
     * ������ �������.
     */
    HOLIDAY_BUS("HOLIDAY_BUS", "������ ��������"),

    /**
     * �������.
     */
    TRAM("TRAM", "�������"),

    /**
     * ����������.
     */
    TROLLEYBUS("TROLLEYBUS", "�����������");

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
