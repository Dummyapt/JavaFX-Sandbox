package de.dummyapt.sandbox.nextpage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NextPage extends Application {

    @Override
    public void start(Stage stage) {
        var borderPane = new BorderPane();
        var firstLabel = new Label("First view!");
        var firstButton = new Button("To the second view!");
        borderPane.setTop(firstButton);
        borderPane.setCenter(firstLabel);

        var vBox = new VBox();
        var secondLabel = new Label("Second view!");
        var second = new Button("To the thirdButton view!");
        vBox.getChildren().addAll(second, secondLabel);

        var gridPane = new GridPane();
        var thirdLabel = new Label("Third view!");
        var thirdButton = new Button("To the firstButton view!");
        gridPane.add(thirdButton, 0, 0);
        gridPane.add(thirdLabel, 0, 1);

        var firstPage = new Scene(borderPane);
        var secondPage = new Scene(vBox);
        var thirdPage = new Scene(gridPane);

        firstButton.setOnAction(ae -> stage.setScene(secondPage));
        second.setOnAction(ae -> stage.setScene(thirdPage));
        thirdButton.setOnAction(ae -> stage.setScene(firstPage));

        stage.setScene(firstPage);
        stage.setResizable(false);
        stage.show();
    }
}