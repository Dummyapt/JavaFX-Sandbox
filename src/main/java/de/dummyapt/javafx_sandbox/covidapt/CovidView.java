package de.dummyapt.javafx_sandbox.covidapt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidView {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();
    private final VBox vBox = new VBox();

    public CovidView(View view) {
        final var id = new TableColumn<Entry, Integer>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        final var lkId = new TableColumn<Entry, Integer>("lkID");
        lkId.setCellValueFactory(new PropertyValueFactory<>("lkId"));

        final var lkName = new TableColumn<Entry, String>("LKName");
        lkName.setCellValueFactory(new PropertyValueFactory<>("lkName"));

        final var value = new TableColumn<Entry, Double>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        final var date = new TableColumn<Entry, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        final var tableView = new TableView<Entry>();
        tableView.getColumns().addAll(Arrays.asList(id, lkId, lkName, value, date));
        tableView.setRowFactory(tv -> {
            final TableRow<Entry> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()))
                    view.setBorderPaneCenter(new EditAndInsertView(row.getItem()).getView());
            });
            return row;
        });

        refreshList();
        tableView.setItems(entries);

        final var search = new TextField();
        search.setPromptText("Enter keyword");
        search.setOnAction(ae -> {
            entries.clear();
            filter(search.getText());
            search.clear();
            tableView.setItems(entries);
        });

        final var submit = new Button("Search");
        submit.setOnAction(search::fireEvent);

        vBox.getChildren().addAll(tableView, new HBox(search, submit));
    }

    private void filter(String search) {
        try {
            final var filter = "'%" + search + "%'";
            final var sql = "SELECT * FROM corona.inzidenzen WHERE id LIKE " + filter +
                    " OR lkid LIKE " + filter +
                    " OR lkname LIKE " + filter +
                    " OR inzidenz LIKE " + filter;
            final var resultSet = Database.getConnection().createStatement().executeQuery(sql);
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
            e.printStackTrace();
        }
    }

    private void refreshList() {
        entries.clear();
        try {
            final var sql = """
                    SELECT *
                    FROM corona.inzidenzen""";
            final var resultSet = Database.getConnection().createStatement().executeQuery(sql);
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
            e.printStackTrace();
        }
    }

    public Node getView() {
        return vBox;
    }
}
