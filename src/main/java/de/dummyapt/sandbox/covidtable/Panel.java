package de.dummyapt.sandbox.covidtable;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Date;

public final class Panel extends Application {
    private final BorderPane borderPane = new BorderPane();
    private final CovidTable covidTable = new CovidTable();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var showDataItem = new MenuItem("_Data");
        showDataItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/show.png")));
        showDataItem.setOnAction(ae -> borderPane.setCenter(covidTable.getTableView()));

        var newEntryItem = new MenuItem("_New");
        newEntryItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/new.png")));
        newEntryItem.setOnAction(ae -> borderPane.setCenter(getEntryPanel()));

        var exitItem = new MenuItem("_Exit");
        exitItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/exit.png")));
        exitItem.setOnAction(ae -> stage.close());

        var lightModeItem = new CheckMenuItem("_Light mode");
        lightModeItem.setSelected(true);

        var darkModeItem = new MenuItem("Dark mode");
        darkModeItem.setDisable(true);

        var infoItem = new MenuItem("_Info");
        infoItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/about.png")));
        infoItem.setOnAction(ae -> borderPane.setCenter(new Label("Made by Dummyapt")));

        var helpItem = new MenuItem("_Help");
        helpItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/help.png")));
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
        refreshButton.setGraphic(new ImageView(new Image("file:src/main/resources/covidtable/images/refresh.png")));
        refreshButton.setOnAction(ae -> borderPane.setCenter(covidTable.getTableView()));

        borderPane.setTop(menuBar);
        borderPane.setCenter(covidTable.getTableView().getPlaceholder());
        borderPane.setBottom(refreshButton);
        BorderPane.setAlignment(refreshButton, Pos.CENTER);

        stage.getIcons().add(new Image("https://cdn-icons-png.flaticon.com/512/2913/2913604.png"));
        stage.setTitle("CovidTable");
        stage.setResizable(false);
        stage.setScene(new Scene(borderPane, 360, 350));
        stage.show();
    }

    private VBox getEntryPanel() {
        var lkIdLabel = new Label("LKID");
        var lkIdInput = new TextField();

        var lkNameLabel = new Label("LKName");
        var lkNameInput = new TextField();

        var valueLabel = new Label("Value (X.YY)");
        var valueInput = new TextField();

        var dateLabel = new Label("Date (YYYY-MM-DD)");
        var datePicker = new DatePicker();

        var statusLabel = new Label();
        var submitButton = new Button("Submit");
        submitButton.setOnAction(ae -> {
            if (covidTable.addEntry(Integer.parseInt(lkIdInput.getText()), lkNameInput.getText(), Double.parseDouble(valueInput.getText()), Date.valueOf(datePicker.getValue())))
                statusLabel.setText("Success!");
            else statusLabel.setText("Error!");
        });

        var vBox = new VBox(new VBox(lkIdLabel, lkIdInput), new VBox(lkNameLabel, lkNameInput), new VBox(valueLabel, valueInput), new VBox(dateLabel, datePicker), submitButton, statusLabel);
        vBox.setSpacing(5);

        return vBox;
    }
}