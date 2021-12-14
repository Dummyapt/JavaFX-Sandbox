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
    private final BorderPane borderPane = new BorderPane();
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
        var showDataItem = new MenuItem("_Data");
        showDataItem.setGraphic(new ImageView(new Image("file:resources/covidtable/images/show.png")));
        showDataItem.setOnAction(ae -> borderPane.setCenter(getTableView()));

        var newEntryItem = new MenuItem("_New");
        newEntryItem.setGraphic(new ImageView(new Image("file:resources/covidtable/images/new.png")));

        var exitItem = new MenuItem("_Exit");
        exitItem.setGraphic(new ImageView(new Image("file:resources/covidtable/images/exit.png")));
        exitItem.setOnAction(ae -> stage.close());

        var lightModeItem = new CheckMenuItem("_Light mode");
        lightModeItem.setSelected(true);

        var darkModeItem = new MenuItem("Dark mode");
        darkModeItem.setDisable(true);

        var infoItem = new MenuItem("_Info");
        infoItem.setGraphic(new ImageView(new Image("file:resources/covidtable/images/about.png")));
        infoItem.setOnAction(ae -> borderPane.setCenter(new Label("Made by Dummyapt")));

        var helpItem = new MenuItem("_Help");
        helpItem.setGraphic(new ImageView(new Image("file:resources/covidtable/images/help.png")));
        helpItem.setOnAction(ae -> getHostServices().showDocument("mailto://support@dummyapt.de"));

        var fileMenu = new Menu("_File");
        fileMenu.getItems().addAll(showDataItem, newEntryItem, exitItem);
        var viewMenu = new Menu("_View");
        viewMenu.getItems().addAll(lightModeItem, darkModeItem);
        var aboutMenu = new Menu("_About");
        aboutMenu.getItems().addAll(infoItem, helpItem);

        var menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, viewMenu, aboutMenu);

        var refreshButton = new Button("_Refresh");
        refreshButton.setGraphic(new ImageView(new Image("file:resources/covidtable/images/refresh.png")));
        refreshButton.setOnAction(ae -> borderPane.setCenter(getTableView()));

        borderPane.setTop(menuBar);
        borderPane.setCenter(getTableView().getPlaceholder());
        borderPane.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);

        var scene = new Scene(borderPane, 360, 350);
        stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/2913/2913604.png"));
        stage.setTitle("CovidTable");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private TableView<Entry> getTableView() {
        var lkId = new TableColumn<Entry, Integer>("lkID");
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
        tableView.refresh();

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