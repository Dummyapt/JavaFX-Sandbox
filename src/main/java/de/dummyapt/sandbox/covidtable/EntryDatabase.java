package de.dummyapt.sandbox.covidtable;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class EntryDatabase {
    private final ArrayList<Entry> entries = new ArrayList<>();

    public EntryDatabase() {
        try {
            var connection = DriverManager.getConnection("jdbc:mariadb://[::1]:3306/corona?user=ebkherne");
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM inzidenzen;");
            while (resultSet.next())
                entries.add(new Entry(resultSet.getString("lkname"),
                        resultSet.getDouble("inzidenz"),
                        resultSet.getDate("datum")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Entry> getEntries() {
        return entries;
    }
}