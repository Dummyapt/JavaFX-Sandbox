package de.dummyapt.javafx_sandbox.tictactoe;

import de.dummyapt.javafx_sandbox.tictactoe.observer.Observable;
import de.dummyapt.javafx_sandbox.tictactoe.observer.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public final class View extends BorderPane implements Observer {
    private static final String DEFAULT = """
            -fx-background-radius: 5em; -fx-color: white;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;
            -fx-font-size: 20px; -fx-font-weight: bold;""";
    private static final String PLAYER_ONE = """
            -fx-background-radius: 5em; -fx-color: #428af5;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;
            -fx-font-size: 20px; -fx-font-weight: bold;""";
    private static final String PLAYER_TWO = """
            -fx-background-radius: 5em; -fx-color: #fa4656;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;
            -fx-font-size: 20px; -fx-font-weight: bold;""";
    private final Button[][] gameField = new Button[3][3];
    private final Label lblStatus = new Label("TicTacToe by Dummyapt");
    private final Observable model;

    public View(Controller pController, Model pModel) {
        model = pModel;

        pModel.registerObserver(this);

        var btnReset = new Button("_Reset");
        btnReset.setOnAction(ae -> pController.reset());
        btnReset.setStyle(DEFAULT);

        var gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));

        for (var column = 0; column < 3; column++) {
            for (var row = 0; row < 3; row++) {
                gameField[column][row] = new Button();
                gameField[column][row].setStyle(DEFAULT);
                int finalRow = row;
                int finalColumn = column;
                gameField[column][row].setOnAction(ae -> pController.setCharacter(finalColumn, finalRow));
                gridPane.add(gameField[column][row], column, row);
                setMargin(gameField[column][row], new Insets(5, 10, 5, 10));
            }
        }

        var textField = new TextField("Press Alt + R to reset the game.");
        textField.setEditable(false);
        textField.setAlignment(Pos.CENTER);

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        setLeft(btnReset);
        setTop(lblStatus);
        setBottom(textField);
        setCenter(gridPane);

        setPadding(new Insets(10, 10, 10, 10));
        setMaxSize(400, 400);

        setAlignment(btnReset, Pos.CENTER);
        setAlignment(lblStatus, Pos.CENTER);

        setMargin(btnReset, new Insets(0, 15, 0, 10));
        setMargin(lblStatus, new Insets(0, 10, 10, 10));
    }

    @Override
    public void update() {
        int[][] gameBoard = model.getGameField();

        for (var column = 0; column < 3; column++)
            for (var row = 0; row < 3; row++)
                switch (gameBoard[column][row]) {
                    case 1 -> {
                        gameField[column][row].setStyle(PLAYER_ONE);
                        gameField[column][row].setText("X");
                    }
                    case 2 -> {
                        gameField[column][row].setStyle(PLAYER_TWO);
                        gameField[column][row].setText("O");
                    }
                    default -> {
                        gameField[column][row].setStyle(DEFAULT);
                        gameField[column][row].setText("");
                    }
                }
        lblStatus.setText(model.getStatusText());
    }

    public Parent getView() {
        return this;
    }
}