package de.dummyapt.sandbox.covidtable;

@SuppressWarnings("unused")
public record Entry(String name, double value) {
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}