package model;

public class Longitude {
    private String value;

    public Longitude(String value) {
        this.value = value;
    }

    public String asString() {
        return value;
    }

    public static Longitude of(String value) {
        return new Longitude(value);
    }
}
