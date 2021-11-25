package de.dummyapt.sandbox;

import javafx.application.Application;
import javafx.stage.Stage;

public final class SandboxFX extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        System.out.println("Working!");

//        stage.setScene();
        stage.setResizable(true);
        stage.show();
    }
}