package de.dummyapt.sandbox.covidtable;

import java.sql.Date;

@SuppressWarnings("unused")
public record Entry(String name, double value, Date date) {
    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}