package de.dummyapt.javafx_sandbox.connectfour;

import de.dummyapt.javafx_sandbox.connectfour.observer.Observable;
import de.dummyapt.javafx_sandbox.connectfour.observer.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public final class View extends BorderPane implements Observer {
    private final Button[][] btnCoins = new Button[7][6];
    private final Label lblStatus = new Label();
    private final Observable model;

    public View(Controller controller, Observable model) {
        this.model = model;

        var btnReset = new Button("Reset");
        var lblTitle = new Label("Connect4 by Dummyapt");

        var gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        for (var column = 0; column < 7; column++) {
            for (var row = 0; row < 6; row++) {
                btnCoins[column][row] = new Button(column + " - " + row);
                btnCoins[column][row].setStyle(
                        "-fx-background-radius: 5em;-fx-color: white;" +
                                "-fx-min-width: 50px;-fx-max-width: 50px;" +
                                "-fx-min-height: 50px;-fx-max-height: 50px;");
                int finalColumn = column;
                btnCoins[column][row].setOnAction(ae -> controller.setColumn(finalColumn));
                gridPane.add(btnCoins[column][row], column, row);
                setMargin(btnCoins[column][row], new Insets(5, 10, 5, 10));
            }
        }
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        setLeft(btnReset);
        setTop(lblStatus);
        setBottom(lblTitle);
        setCenter(gridPane);

        setPadding(new Insets(10, 10, 10, 10));
        setMaxSize(400, 400);

        setAlignment(btnReset, Pos.CENTER);
        setAlignment(lblStatus, Pos.CENTER);
        setAlignment(lblTitle, Pos.CENTER);

        setMargin(btnReset, new Insets(0, 15, 0, 10));
        setMargin(lblStatus, new Insets(0, 10, 10, 10));
        setMargin(lblTitle, new Insets(10, 0, 15, 0));

        btnReset.setOnAction(ae -> controller.reset());
        btnReset.setStyle(
                "-fx-background-radius: 5em;" +
                        "-fx-min-width: 100px; -fx-max-width: 100px;" +
                        "-fx-min-height: 50px; -fx-max-height: 50px;");
    }

    @Override
    public void update() {
        int[][] gameBoard = model.getGameField();
        for (var column = 0; column < 7; column++)
            for (var row = 0; row < 6; row++)
                if (gameBoard[row][column] == 1)
                    btnCoins[column][row].setStyle(
                            "-fx-background-radius: 5em; -fx-color: yellow;" +
                                    "-fx-min-width: 50px; -fx-max-width: 50px;" +
                                    "-fx-min-height: 50px; -fx-max-height: 50px;");
                else if (gameBoard[row][column] == 2)
                    btnCoins[column][row].setStyle(
                            "-fx-background-radius: 5em; -fx-color: red;" +
                                    "-fx-min-width: 50px; -fx-max-width: 50px;" +
                                    "-fx-min-height: 50px; -fx-max-height: 50px");
                else
                    btnCoins[column][row].setStyle(
                            "-fx-background-radius: 5em; -fx-color: white;" +
                                    "-fx-min-width: 50px; -fx-max-width: 50px;" +
                                    "-fx-min-height: 50px; -fx-max-height: 50px;");
        lblStatus.setText(model.getStatusText());
    }

    public Parent getView() {
        return this;
    }
}