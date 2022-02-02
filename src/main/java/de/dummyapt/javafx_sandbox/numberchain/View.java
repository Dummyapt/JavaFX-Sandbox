package de.dummyapt.javafx_sandbox.numberchain;

import de.dummyapt.javafx_sandbox.numberchain.observer.Observable;
import de.dummyapt.javafx_sandbox.numberchain.observer.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public final class View implements Observer {
    private static final String STYLE_DEFAULT_CARD = """
            -fx-background-radius: 5em; -fx-color: #ffffff;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;""";
    private static final String STYLE_RIGHT_CARD = """
            -fx-background-radius: 5em; -fx-color: #4ce578;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;
            -fx-font-size: 20px; -fx-font-weight: bold;""";
    private static final String STYLE_WRONG_CARD = """
            -fx-background-radius: 5em; -fx-color: #f14a5c;
            -fx-min-width: 50px; -fx-max-width: 50px;
            -fx-min-height: 50px; -fx-max-height: 50px;
            -fx-font-size: 20px; -fx-font-weight: bold;""";
    private final Button[][] gameField = new Button[Model.FIELD_SIZE][Model.FIELD_SIZE];
    private final BorderPane borderPane = new BorderPane();
    private final Label statusLabel = new Label(String.format("NumberChain%nPlayer 1 begins!"));
    private final Observable model;

    public View(Controller controller, Model model) {
        this.model = model;

        var btnExit = new Button("_Exit");
        btnExit.setStyle(STYLE_DEFAULT_CARD);
        btnExit.setOnAction(ae -> {
            try {
                controller.stop();
            } catch (Exception e) {
                e.getLocalizedMessage();
            }
        });

        var btnReset = new Button("_Reset");
        btnReset.setStyle(STYLE_DEFAULT_CARD);
        btnReset.setOnAction(ae -> controller.reset());

        var hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(btnReset, btnExit);

        var gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        for (var column = 0; column < Model.FIELD_SIZE; column++) {
            for (var row = 0; row < Model.FIELD_SIZE; row++) {
                gameField[column][row] = new Button();
                gameField[column][row].setStyle(STYLE_DEFAULT_CARD);
                gridPane.add(gameField[column][row], column, row);

                final var finalRow = row;
                final var finalColumn = column;
                gameField[column][row].setOnAction(ae -> controller.reveal(finalColumn, finalRow));
            }
        }

        BorderPane.setAlignment(statusLabel, Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        BorderPane.setMargin(statusLabel, new Insets(5, 5, 5, 5));
        BorderPane.setMargin(gridPane, new Insets(5, 5, 5, 5));
        BorderPane.setMargin(hBox, new Insets(5, 5, 5, 5));

        borderPane.setPadding(new Insets(5, 5, 5, 5));

        borderPane.setTop(statusLabel);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hBox);
    }

    @Override
    public void update() {
        var gameBoard = model.getGameField();
        for (var column = 0; column < Model.FIELD_SIZE; column++) {
            for (var row = 0; row < Model.FIELD_SIZE; row++) {
                switch (gameBoard[column][row].getBelongsToPlayer()) {
                    case 1, 2 -> {
                        gameField[row][column].setStyle(STYLE_RIGHT_CARD);
                        gameField[row][column].setText(Integer.toString(gameBoard[column][row].getNumber()));
                    }
                    case 3 -> {
                        gameField[row][column].setStyle(STYLE_WRONG_CARD);
                        gameField[row][column].setText(Integer.toString(gameBoard[column][row].getNumber()));
                    }
                    default -> {
                        gameField[row][column].setStyle(STYLE_DEFAULT_CARD);
                        gameField[row][column].setText(null);
                    }
                }
            }
        }

        statusLabel.setText(model.getStatusText());
    }

    public Parent getView() {
        return borderPane;
    }
}