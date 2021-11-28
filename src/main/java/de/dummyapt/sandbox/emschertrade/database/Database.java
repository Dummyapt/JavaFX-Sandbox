package de.dummyapt.sandbox.emschertrade.database;

import de.dummyapt.sandbox.emschertrade.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public final class Database {
    private final ObservableList<Product> entries = FXCollections.observableArrayList();
    private final String port;
    private final String username;
    private final String password;
    private final String database;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Database(String port, String username, String password, String database) {
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public void connect() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://[::1]:" + port + "/" + database, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format("SELECT * FROM %s.products", database));
            while (resultSet.next())
                entries.add(new Product(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price")));
        } catch (SQLException e) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Something isn't going as planned!");
            alert.setContentText("Please check your credentials and make sure the database exists!");
            alert.showAndWait();
        } finally {
            assert resultSet != null;
            resultSet.close();
            statement.close();
            connection.close();
        }
    }

    public void createEntry(String name, double price) throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://[::1]:" + port + "/" + database, username, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format("INSERT INTO %s.products (name, price) VALUES ('%s', %s)", database, name, price));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert statement != null;
            statement.close();
            connection.close();
        }
    }

    public ObservableList<Product> getEntries() {
        return entries;
    }
}