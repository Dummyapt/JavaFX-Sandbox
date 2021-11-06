package de.dummyapt.sandbox.mytableview;

@SuppressWarnings("unused")
public record State(String name, double value) {
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}