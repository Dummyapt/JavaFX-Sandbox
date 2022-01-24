package de.dummyapt.javafx_sandbox.projektwoche;

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
        Scene scene = new Scene(view.getView());
        stage.setTitle("Projektwoche");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        model.removeObserver(view);
        System.exit(-1);
    }

    public void changeArduino(int id) {
        model.changeArduino(id);
    }
}
