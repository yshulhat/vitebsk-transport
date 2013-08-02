package by.sands.vitebsktransport.domain;

public class Route extends DBObject {
    private String name;
    private String number;
    private String type;

    public Route() {
    }

    public Route(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public Route(String number, String name, String type) {
        this.number = number;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return number + ":  " + name;
    }

}
