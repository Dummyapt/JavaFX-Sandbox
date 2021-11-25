package de.dummyapt.sandbox.randomnumber;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.SecureRandom;

public final class RandomNumber extends Application {
    private static final String ERROR = "There was an error!";
    private final TextField inputMin = new TextField();
    private final TextField inputMax = new TextField();
    private final TextField output = new TextField();
    private final Label labelMin = new Label("From:");
    private final Label labelMax = new Label("To:");
    private final Label labelOutput = new Label("Your number");
    private final GridPane gridPane = new GridPane();
    private final Button button = new Button("_GO!");
    private final Alert alert = new Alert(Alert.AlertType.ERROR);

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        designPanel();
        addContent();
        createButton();

        var scene = new Scene(gridPane, 225, 160);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("RandomNumber");
        stage.show();
    }

    private void createButton() {
        button.setOnAction(ae -> {
            try {
                if (inputMin.getText().contains("-") || inputMax.getText().contains("-")) {
                    alert.setTitle("ERROR - Not supported");
                    alert.setHeaderText(ERROR);
                    alert.setContentText("Negative numbers are not supported!");
                    alert.showAndWait();
                    return;
                }

                if (inputMin.getText().isEmpty() || inputMax.getText().isEmpty()) {
                    var emptyField = "";
                    if (inputMin.getText().isEmpty() && inputMax.getText().isEmpty())
                        emptyField = "From, To";
                    else if (!inputMin.getText().isEmpty() && inputMax.getText().isEmpty())
                        emptyField = "To";
                    else if (inputMin.getText().isEmpty() && !inputMax.getText().isEmpty())
                        emptyField = "From";

                    alert.setTitle("ERROR - Field empty");
                    alert.setHeaderText(ERROR);
                    alert.setContentText("The following fields are empty: " + emptyField);
                    alert.showAndWait();
                    return;
                }

                if (!inputMin.getText().matches("\\d+") || !inputMax.getText().matches("\\d+")) {
                    alert.setTitle("ERROR - Only numbers");
                    alert.setHeaderText(ERROR);
                    alert.setContentText("Only use numbers!");
                    alert.showAndWait();
                    return;
                }

                var min = Integer.parseInt(inputMin.getText());
                var max = Integer.parseInt(inputMax.getText());

                if (max > min) {
                    min = Integer.parseInt(inputMin.getText());
                    max = Integer.parseInt(inputMax.getText());
                } else if (min > max) {
                    min = Integer.parseInt(inputMax.getText());
                    max = Integer.parseInt(inputMin.getText());
                }
                output.setText(String.valueOf(new SecureRandom().nextInt(max) + min));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addContent() {
        gridPane.add(labelMin, 0, 0);
        gridPane.add(inputMin, 0, 1);
        gridPane.add(labelMax, 1, 0);
        gridPane.add(inputMax, 1, 1);
        gridPane.add(labelOutput, 0, 2, 2, 1);
        gridPane.add(output, 0, 3, 2, 1);
        gridPane.add(button, 0, 4, 2, 1);
    }

    private void designPanel() {
        button.setMinWidth(75);
        output.setMaxWidth(110);
        output.setAlignment(Pos.CENTER);
        output.setEditable(false);
        GridPane.setMargin(labelOutput, new Insets(10, 0, 0, 0));
        GridPane.setHalignment(button, HPos.CENTER);
        GridPane.setHalignment(labelOutput, HPos.CENTER);
        GridPane.setHalignment(output, HPos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));
    }
}