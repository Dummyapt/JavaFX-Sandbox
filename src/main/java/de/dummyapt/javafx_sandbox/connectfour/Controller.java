package de.dummyapt.javafx_sandbox.connectfour;

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
        final var scene = new Scene(view.getView());
        stage.setScene(scene);
        stage.setTitle("Connect4 by Dummyapt");
        stage.setResizable(false);
        stage.show();
    }

    public void reset() {
        model.reset();
    }

    public void setColumn(int pCol) {
        model.setTile(pCol);
    }
}
