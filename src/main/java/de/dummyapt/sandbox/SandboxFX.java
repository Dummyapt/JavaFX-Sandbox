package de.dummyapt.sandbox;

import javafx.application.Application;
import javafx.stage.Stage;

public class SandboxFX extends Application {

    @Override
    public void start(Stage stage) {
        System.out.println("Working!");

//        stage.setScene();
        stage.setResizable(true);
        stage.show();
    }
}