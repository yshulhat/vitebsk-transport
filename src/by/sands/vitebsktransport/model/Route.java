package by.sands.vitebsktransport.model;

public class Route extends DBObject {
    private String name;
    private String number;

    public Route() {
    }

    public Route(String number, String name) {
        this.number = number;
        this.name = name;
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

    @Override
    public String toString() {
        return number + ":  " + name;
    }

}
