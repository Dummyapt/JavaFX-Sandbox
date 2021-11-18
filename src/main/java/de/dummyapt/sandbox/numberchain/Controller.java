package de.dummyapt.sandbox.numberchain;

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
    public void start(Stage stage) {
        var scene = new Scene(view.getView());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        model.removeObserver(view);
        super.stop();
        System.exit(-1);
    }

    public void reveal(int column, int row) {
        model.setCard(column, row);
    }

    public void reset() {
        model.reset();
    }
}