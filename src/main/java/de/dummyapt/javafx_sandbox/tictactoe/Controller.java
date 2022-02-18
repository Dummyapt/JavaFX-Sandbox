package de.dummyapt.javafx_sandbox.tictactoe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Controller extends Application {
    private Model model;
    private View view;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        model = new Model();
        view = new View(this, model);
        model.registerObserver(view);
    }

    @Override
    public void start(Stage stage) {
        var scene = new Scene(view.getView());
        stage.setScene(scene);
        stage.setTitle("TicTacToe | Dummyapt");
        stage.setResizable(false);
        stage.show();
    }

    public void setCharacter(int pCol, int pRow) {
        model.setTile(pCol, pRow);
    }

    public void reset() {
        model.reset();
    }
}
