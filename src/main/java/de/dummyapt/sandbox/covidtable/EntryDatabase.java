package de.dummyapt.sandbox.covidtable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

final class EntryDatabase {
    private static final String URL = "jdbc:mariadb://[::1]:3306/corona?user=ebkherne";
    private static final String QUERY = "SELECT * FROM inzidenzen";
    private final ArrayList<Entry> entries = new ArrayList<>();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    EntryDatabase() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QUERY);
            while (resultSet.next())
                entries.add(new Entry(resultSet.getString("lkname"),
                        resultSet.getDouble("inzidenz"),
                        resultSet.getDate("datum")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            assert resultSet != null;
            assert statement != null;
            assert connection != null;
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    List<Entry> getEntries() {
        return entries;
    }
}