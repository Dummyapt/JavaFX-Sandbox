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

public final class View implements Observer {
    private static final String WHITE_STYLE = """
            -fx-background-radius: 5em; -fx-color: white;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;""";
    private static final String RED_STYLE = """
            -fx-background-radius: 5em; -fx-color: red;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;""";
    private static final String YELLOW_STYLE = """
            -fx-background-radius: 5em; -fx-color: yellow;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;""";
    private final BorderPane borderPane = new BorderPane();
    private final Button[][] btnCoins = new Button[7][6];
    private final Label lblStatus = new Label();
    private final Observable model;

    public View(Controller controller, Observable model) {
        this.model = model;

        final var lblTitle = new Label("Connect4 by Dummyapt");

        final var btnReset = new Button("Reset");
        btnReset.setOnAction(ae -> controller.reset());
        btnReset.setStyle(WHITE_STYLE);

        final var gridPane = new GridPane();
        for (var column = 0; column < 7; column++) {
            for (var row = 0; row < 6; row++) {
                btnCoins[column][row] = new Button(column + " - " + row);
                btnCoins[column][row].setStyle(WHITE_STYLE);
                final int finalColumn = column;
                btnCoins[column][row].setOnAction(ae -> controller.setColumn(finalColumn));
                gridPane.add(btnCoins[column][row], column, row);
                BorderPane.setMargin(btnCoins[column][row], new Insets(5, 10, 5, 10));
            }
        }
        gridPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        borderPane.setLeft(btnReset);
        borderPane.setTop(lblStatus);
        borderPane.setBottom(lblTitle);
        borderPane.setCenter(gridPane);
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setMaxSize(400, 400);
        BorderPane.setAlignment(btnReset, Pos.CENTER);
        BorderPane.setAlignment(lblStatus, Pos.CENTER);
        BorderPane.setAlignment(lblTitle, Pos.CENTER);
        BorderPane.setMargin(btnReset, new Insets(0, 15, 0, 10));
        BorderPane.setMargin(lblStatus, new Insets(0, 10, 10, 10));
        BorderPane.setMargin(lblTitle, new Insets(10, 0, 15, 0));
    }

    @Override
    public void update() {
        final int[][] gameBoard = model.getGameField();
        for (var column = 0; column < 7; column++)
            for (var row = 0; row < 6; row++)
                if (gameBoard[row][column] == 1) btnCoins[column][row].setStyle(YELLOW_STYLE);
                else if (gameBoard[row][column] == 2) btnCoins[column][row].setStyle(RED_STYLE);
                else btnCoins[column][row].setStyle(WHITE_STYLE);
        lblStatus.setText(model.getStatusText());
    }

    public Parent getView() {
        return borderPane;
    }
}
