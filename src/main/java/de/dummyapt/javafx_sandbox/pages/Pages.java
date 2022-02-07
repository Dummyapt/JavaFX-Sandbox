package de.dummyapt.javafx_sandbox.pages;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public final class Pages extends Application {
    private final Button borderPaneButton = new Button("To the second view!");
    private final Button vBoxButton = new Button("To the third view!");
    private final Button gridPaneButton = new Button("To the first view!");

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        borderPaneButton.setOnAction(ae -> stage.setScene(getVBox()));
        vBoxButton.setOnAction(ae -> stage.setScene(getGridPane()));
        gridPaneButton.setOnAction(ae -> stage.setScene(getBorderPane()));

        stage.setScene(getBorderPane());
        stage.setResizable(false);
        stage.show();
    }

    private Scene getBorderPane() {
        final var borderPane = new BorderPane();
        borderPane.setTop(borderPaneButton);
        borderPane.setCenter(new Label("First view!"));
        return new Scene(borderPane);
    }

    private Scene getGridPane() {
        final var gridPane = new GridPane();
        gridPane.add(gridPaneButton, 0, 0);
        gridPane.add(new Label("Third view!"), 0, 1);
        return new Scene(gridPane);
    }

    private Scene getVBox() {
        final var vBox = new VBox();
        vBox.getChildren().addAll(vBoxButton, new Label("Second view!"));
        return new Scene(vBox);
    }
}