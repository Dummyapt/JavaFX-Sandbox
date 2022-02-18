package de.dummyapt.javafx_sandbox.exchangerapt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Controller extends Application {
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
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public void reset() {
        model.reset();
    }

    public void exchange(double total) {
        model.exchange(total);
    }
}
