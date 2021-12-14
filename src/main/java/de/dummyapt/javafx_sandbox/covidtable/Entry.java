package de.dummyapt.javafx_sandbox.covidtable;

import java.sql.Date;

public record Entry(int id, int lkId, String lkName, double value, Date date) {
    public int getId() {
        return id;
    }

    public int getLkId() {
        return lkId;
    }

    public String getLkName() {
        return lkName;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }
}