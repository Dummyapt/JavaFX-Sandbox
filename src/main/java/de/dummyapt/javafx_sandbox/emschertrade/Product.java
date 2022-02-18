package de.dummyapt.javafx_sandbox.emschertrade;

public record Product(int id, String name, double price) {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
