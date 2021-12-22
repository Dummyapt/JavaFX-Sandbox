package de.dummyapt.javafx_sandbox.covidtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidTable {
    private final TableView<Entry> tableView;
    private Connection connection;

    public CovidTable() {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        var lkId = new TableColumn<Entry, Integer>("lkID");
        lkId.setCellValueFactory(new PropertyValueFactory<>("lkId"));
        var lkName = new TableColumn<Entry, String>("LKName");
        lkName.setCellValueFactory(new PropertyValueFactory<>("lkName"));
        var value = new TableColumn<Entry, Double>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        var date = new TableColumn<Entry, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView = new TableView<>();
        tableView.getColumns().addAll(Arrays.asList(lkId, lkName, value, date));
        tableView.setPlaceholder(new Label("No data"));
    }

    TableView<Entry> getTableView() {
        tableView.setItems(getEntries());
        return tableView;
    }

    private ObservableList<Entry> getEntries() {
        ObservableList<Entry> entries = FXCollections.observableArrayList();
        try {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM corona.inzidenzen;");
            while (resultSet.next())
                entries.add(new Entry(resultSet.getInt("id"), resultSet.getInt("lkid"), resultSet.getString("lkname"), resultSet.getDouble("inzidenz"), resultSet.getDate("datum")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }

    boolean addEntry(int lkId, String lkName, double value, Date date) {
        try {
            var preparedStatement = connection.prepareStatement("INSERT INTO corona.inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, String.valueOf(lkId));
            preparedStatement.setString(2, lkName);
            preparedStatement.setString(3, String.valueOf(value));
            preparedStatement.setString(4, String.valueOf(date));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}