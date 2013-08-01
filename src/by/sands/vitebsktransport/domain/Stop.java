package by.sands.vitebsktransport.domain;

public class Stop extends DBObject {
    private String name;
    private String coords;

    public Stop() {
    }

    public Stop(String name, String coords) {
        super();
        this.name = name;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    @Override
    public String toString() {
        return name;
    }

}
