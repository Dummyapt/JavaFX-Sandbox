package de.dummyapt.javafx_sandbox.covidapt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Predicate;

public final class CovidView {
    private final ObservableList<Entry> entries = FXCollections.observableArrayList();
    private final FilteredList<Entry> filteredList = new FilteredList<>(entries);
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
        tableView.setItems(filteredList);

        var search = new TextField();
        search.setPromptText("Suchbegriff eingeben");
        search.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(createPredicate(newValue)));
        vBox.getChildren().addAll(tableView, search);
    }

    private void refreshList() {
        entries.clear();
        try {
            var resultSet = Database.getConnection().createStatement().executeQuery("SELECT * FROM inzidenzen");
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

    private Predicate<Entry> createPredicate(String searchText) {
        return entry -> {
            if (searchText == null || searchText.isEmpty())
                return true;
            return searchFindsIncidence(entry, searchText);
        };
    }

    private boolean searchFindsIncidence(Entry entry, String searchText) {
        return (
                String.valueOf(entry.getId()).contains(searchText) ||
                        String.valueOf(entry.getLkId()).contains(searchText) ||
                        entry.getLkName().toLowerCase().contains(searchText) ||
                        String.valueOf(entry.getValue()).contains(searchText) ||
                        String.valueOf(entry.getDate()).contains(searchText)
        );
    }

    public Node getView() {
        return vBox;
    }
}
