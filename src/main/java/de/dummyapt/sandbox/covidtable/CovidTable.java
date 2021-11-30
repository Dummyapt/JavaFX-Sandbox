package de.dummyapt.sandbox.covidtable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidTable extends Application {
    private Connection connection;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) throws SQLException {
        var lkId = new TableColumn<Entry, Integer>("LKID");
        lkId.setCellValueFactory(new PropertyValueFactory<>("lkId"));
        var lkName = new TableColumn<Entry, String>("LKName");
        lkName.setCellValueFactory(new PropertyValueFactory<>("lkName"));
        var value = new TableColumn<Entry, Double>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        var date = new TableColumn<Entry, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        var tableView = new TableView<Entry>();
        tableView.getColumns().addAll(Arrays.asList(lkId, lkName, value, date));
        tableView.setPlaceholder(new Label("No data"));
        tableView.setItems(getEntries());

        var refreshButton = new Button("_Refresh");
        refreshButton.setOnAction(ae -> {
            tableView.setItems(getEntries());
            tableView.refresh();
        });

        var borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);

        var scene = new Scene(borderPane);
        var icon = new Image("https://cdn.pixabay.com/photo/2020/04/29/08/24/coronavirus-5107804_960_720.png");
        stage.setTitle("CovidTable");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Entry> getEntries() {
        ObservableList<Entry> entries = FXCollections.observableArrayList();
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM corona.inzidenzen");
            while (resultSet.next())
                entries.add(
                        new Entry(
                                resultSet.getInt("id"),
                                resultSet.getInt("lkid"),
                                resultSet.getString("lkname"),
                                resultSet.getDouble("inzidenz"),
                                resultSet.getDate("datum")
                        )
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}