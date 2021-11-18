package de.dummyapt.sandbox.connectfour;

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
        model.registerObserver(() -> System.out.println(model.getStatusText()));
    }

    @Override
    public void start(Stage primaryStage) {
        var primaryScene = new Scene(view.getView());
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("Connect4 by Dummyapt");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void reset() {
        model.reset();
    }

    public void setColumn(int pCol) {
        model.setTile(pCol);
    }
}