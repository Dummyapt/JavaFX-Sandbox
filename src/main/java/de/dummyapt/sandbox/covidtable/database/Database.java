package de.dummyapt.sandbox.covidtable.database;

import de.dummyapt.sandbox.covidtable.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public final class Database {
    private static final String URL = "jdbc:mariadb://[::1]:3306/corona?user=ebkherne";
    private static final String QUERY = "SELECT * FROM inzidenzen;";
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Database() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(QUERY);
            while (resultSet.next())
                entries.add(new Entry(resultSet.getString("lkname"),
                        resultSet.getDouble("inzidenz"),
                        resultSet.getDate("datum")));
        } catch (SQLException e) {
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

    public ObservableList<Entry> getEntries() {
        return entries;
    }
}