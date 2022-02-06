package de.dummyapt.javafx_sandbox.covidapt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidView {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();
    private final VBox vBox = new VBox();

    public CovidView() {
        var id = new TableColumn<Entry, Integer>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        var lkId = new TableColumn<Entry, Integer>("lkID");
        lkId.setCellValueFactory(new PropertyValueFactory<>("lkId"));
        var lkName = new TableColumn<Entry, String>("LKName");
        lkName.setCellValueFactory(new PropertyValueFactory<>("lkName"));
        var value = new TableColumn<Entry, Double>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        var date = new TableColumn<Entry, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        var tableView = new TableView<Entry>();
        tableView.getColumns().addAll(Arrays.asList(id, lkId, lkName, value, date));

        refreshList();
        tableView.setItems(entries);

        var search = new TextField();
        search.setPromptText("Suchbegriff eingeben");

        var submit = new Button("Search");
        submit.setOnAction(ae -> {
            entries.clear();
            filter(search.getText());
            search.clear();
            tableView.setItems(entries);
        });
        search.setOnAction(ae -> submit.fire());
        vBox.getChildren().addAll(tableView, new HBox(search, submit));
    }

    private void filter(String search) {
        try {
            var filter = "'%" + search + "%'";
            var sql = "SELECT * FROM corona.inzidenzen WHERE id LIKE " + filter +
                    " OR lkid LIKE " + filter +
                    " OR lkname LIKE " + filter +
                    " OR inzidenz LIKE " + filter;
            var resultSet = Database.getConnection().createStatement().executeQuery(sql);
            while (resultSet.next())
                entries.add(
                        new Entry(
                                resultSet.getInt("id"),
                                resultSet.getInt("lkId"),
                                resultSet.getString("lkname"),
                                resultSet.getDouble("inzidenz"),
                                resultSet.getDate("datum"))
                );
        } catch (SQLException e) {
            e.e.printStackTrace();
        }
    }

    private void refreshList() {
        entries.clear();
        try {
            var resultSet = Database.getConnection().createStatement().executeQuery("SELECT * FROM corona.inzidenzen");
            while (resultSet.next())
                entries.add(
                        new Entry(
                                resultSet.getInt("id"),
                                resultSet.getInt("lkId"),
                                resultSet.getString("lkname"),
                                resultSet.getDouble("inzidenz"),
                                resultSet.getDate("datum"))
                );
        } catch (SQLException e) {
            e.e.printStackTrace();
        }
    }

    public Node getView() {
        return vBox;
    }
}
