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
        TableColumn<Entry, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Entry, Double> value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        TableColumn<Entry, Date> date = new TableColumn<>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableView<Entry> tableView = new TableView<>();
        tableView.getColumns().addAll(Arrays.asList(name, value, date));
        tableView.setItems(getEntries());
        tableView.setPlaceholder(new Label("No data"));

        var refreshButton = new Button("_Refresh");
        refreshButton.setOnAction(ae -> {
            tableView.setItems(getEntries());
            tableView.refresh();
        });

        var borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);

        var scene = new Scene(borderPane, 315, 400);
        var icon = new Image("https://cdn.pixabay.com/photo/2020/04/29/08/24/coronavirus-5107804_960_720.png");
        stage.setScene(scene);
        stage.setTitle("Covid Table");
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.show();
    }

    private ObservableList<Entry> getEntries() {
        ObservableList<Entry> entries = FXCollections.observableArrayList();
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM inzidenzen");
            while (resultSet.next())
                entries.add(new Entry(resultSet.getString("lkname"),
                        resultSet.getDouble("inzidenz"),
                        resultSet.getDate("datum")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }
}