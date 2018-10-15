package model;

public class Longitude {
    private double value;

    public Longitude(double value) {
        this.value = value;
    }

    public double asDouble() {
        return value;
    }

    public static Longitude of(double value) {
        return new Longitude(value);
    }
}
