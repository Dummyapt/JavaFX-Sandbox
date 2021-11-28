package de.dummyapt.sandbox.covidtable.database;

import de.dummyapt.sandbox.covidtable.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public final class Database {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();
    private final String url;
    private final String query;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Database(String url, String query) {
        this.url = url;
        this.query = query;
    }

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next())
                entries.add(new Entry(resultSet.getString("lkname"),
                        resultSet.getDouble("inzidenz"),
                        resultSet.getDate("datum")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert resultSet != null;
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    public ObservableList<Entry> getEntries() {
        return entries;
    }
}