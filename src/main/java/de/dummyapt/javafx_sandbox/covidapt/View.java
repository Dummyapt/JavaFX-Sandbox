package de.dummyapt.javafx_sandbox.covidapt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public final class View extends Application {
    final BorderPane borderPane = new BorderPane();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var covidView = new CovidView(this);
        var insertView = new EditAndInsertView();

        var showDataItem = new MenuItem("_Data");
        showDataItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidapt/show.png")));
        showDataItem.setOnAction(ae -> borderPane.setCenter(covidView.getView()));

        var newEntryItem = new MenuItem("_New");
        newEntryItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidapt/new.png")));
        newEntryItem.setOnAction(ae -> borderPane.setCenter(insertView.getView()));

        var exitItem = new MenuItem("Exit");
        exitItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidapt/exit.png")));
        exitItem.setOnAction(ae -> stage.close());

        var lightModeItem = new CheckMenuItem("Light mode");
        lightModeItem.setSelected(true);

        var darkModeItem = new MenuItem("Dark mode");
        darkModeItem.setDisable(true);

        var infoItem = new MenuItem("_Info");
        infoItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidapt/about.png")));
        infoItem.setOnAction(ae -> borderPane.setCenter(new Label("Made by Dummyapt")));

        var helpItem = new MenuItem("_Help");
        helpItem.setGraphic(new ImageView(new Image("file:src/main/resources/covidapt/help.png")));
        helpItem.setOnAction(ae -> getHostServices().showDocument("mailto://support@dummyapt.de"));

        var fileMenu = new Menu("_File");
        fileMenu.getItems().addAll(showDataItem, newEntryItem, exitItem);
        var viewMenu = new Menu("_View");
        viewMenu.getItems().addAll(lightModeItem, darkModeItem);
        var aboutMenu = new Menu("_About");
        aboutMenu.getItems().addAll(infoItem, helpItem);

        var menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, viewMenu, aboutMenu);

        borderPane.setTop(menuBar);
        borderPane.setCenter(covidView.getView());

        stage.getIcons().add(new Image("file:src/main/resources/covidapt/icon.png"));
        stage.setTitle("CovidView");
        stage.setResizable(false);
        stage.setScene(new Scene(borderPane, 450, 350));
        stage.show();
    }
}
