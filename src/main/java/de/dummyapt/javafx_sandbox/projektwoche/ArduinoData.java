package de.dummyapt.javafx_sandbox.projektwoche;

public class ArduinoData {
    private int id;
    private String location;
    private double temperature;
    private double humidity;

    public ArduinoData(int id, String location, double temperature, double humidity) {
        this.id = id;
        this.location = location;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
