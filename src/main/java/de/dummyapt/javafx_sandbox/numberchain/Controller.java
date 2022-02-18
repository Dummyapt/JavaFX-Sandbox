package de.dummyapt.javafx_sandbox.numberchain;

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
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void reveal(int column, int row) {
        model.setCard(column, row);
    }

    public void reset() {
        model.reset();
    }
}
