package model;

public class Latitude {
    private double value;

    public Latitude(double value) {
        this.value = value;
    }

    public double asDouble() {
        return value;
    }

    public static Latitude of(double value) {
        return new Latitude(value);
    }
}
