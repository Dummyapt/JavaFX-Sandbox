package de.dummyapt.sandbox.covidtable;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidTable extends Application {
    private Connection connection;
    private final BorderPane borderPane = new BorderPane();
    private final TableView<Entry> tableView = new TableView<>();

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
        var lensIcon = new ImageView(new Image("https://cdn-icons-png.flaticon.com/512/694/694985.png"));
        lensIcon.setFitHeight(25);
        lensIcon.setFitWidth(25);
        var sheetIcon = new ImageView(new Image("https://static.thenounproject.com/png/992729-200.png"));
        sheetIcon.setFitHeight(25);
        sheetIcon.setFitWidth(25);
        var exitIcon = new ImageView(new Image("https://findicons.com/files/icons/2711/free_icons_for_windows8_metro/512/exit.png"));
        exitIcon.setFitHeight(25);
        exitIcon.setFitWidth(25);
        var starIcon = new ImageView(new Image("https://image.flaticon.com/icons/png/512/69/69544.png"));
        starIcon.setFitHeight(25);
        starIcon.setFitWidth(25);
        var speakIcon = new ImageView(new Image("http://cdn.onlinewebfonts.com/svg/img_261633.png"));
        speakIcon.setFitHeight(25);
        speakIcon.setFitWidth(25);

        var dataItem = new MenuItem("_Data");
        dataItem.setGraphic(lensIcon);
        dataItem.setOnAction(ae -> {
            createTable();
            borderPane.setCenter(tableView);
        });
        var newItem = new MenuItem("_New");
        newItem.setGraphic(sheetIcon);
        var exitItem = new MenuItem("_Exit");
        exitItem.setGraphic(exitIcon);
        var lightModeItem = new CheckMenuItem("_Light mode");
        var darkModeItem = new MenuItem("_Dark mode");
        darkModeItem.setDisable(true);
        var infoItem = new MenuItem("_Info");
        infoItem.setGraphic(starIcon);
        var helpItem = new MenuItem("_Help");
        helpItem.setGraphic(speakIcon);

        var fileMenu = new Menu("_File");
        fileMenu.getItems().addAll(dataItem, newItem, exitItem);
        var viewMenu = new Menu("_View");
        viewMenu.getItems().addAll(lightModeItem, darkModeItem);
        var aboutMenu = new Menu("_About");
        aboutMenu.getItems().addAll(infoItem, helpItem);

        var menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, viewMenu, aboutMenu);

        var refreshIcon = new ImageView(new Image("https://icons.veryicon.com/png/o/system/avuex-cli-icon/refresh-165.png"));
        refreshIcon.setFitHeight(25);
        refreshIcon.setFitWidth(25);

        var refreshButton = new Button("_Refresh");
        refreshButton.setGraphic(refreshIcon);
        refreshButton.setOnAction(ae -> {
            tableView.setItems(getEntries());
            tableView.refresh();
        });

        borderPane.setTop(menuBar);
        borderPane.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);

        var scene = new Scene(borderPane, 360, 250);
        var icon = new Image("https://cdn.pixabay.com/photo/2020/04/29/08/24/coronavirus-5107804_960_720.png");
        stage.setTitle("CovidTable");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private TableView<Entry> createTable() {
        var lkId = new TableColumn<Entry, Integer>("LKID");
        lkId.setCellValueFactory(new PropertyValueFactory<>("lkId"));
        var lkName = new TableColumn<Entry, String>("LKName");
        lkName.setCellValueFactory(new PropertyValueFactory<>("lkName"));
        var value = new TableColumn<Entry, Double>("Value");
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        var date = new TableColumn<Entry, Date>("Date");
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableView.getColumns().addAll(Arrays.asList(lkId, lkName, value, date));
        tableView.setPlaceholder(new Label("No data"));
        tableView.setItems(getEntries());
        return tableView;
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