package model;

public class Latitude {
    private String value;

    public Latitude(String value) {
        this.value = value;
    }

    public String asString() {
        return value;
    }

    public static Latitude of(String value) {
        return new Latitude(value);
    }
}
