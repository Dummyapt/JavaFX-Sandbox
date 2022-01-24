package de.dummyapt.javafx_sandbox.projektwoche;

import de.dummyapt.javafx_sandbox.projektwoche.observer.Observable;
import de.dummyapt.javafx_sandbox.projektwoche.observer.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;

// TODO: 24.01.2022 Maybe port to a project without MVC pattern
public final class View implements Observer {
    private final BorderPane borderPane = new BorderPane();
    private final Controller controller;
    private final Observable model;

    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;

        Button[] buttons = new Button[]{new Button("ArduinoData 1"), new Button("ArduinoData 2"), new Button("ArduinoData 3"), new Button("ArduinoData 4"), new Button("ArduinoData 5")};

        int id = model.getId(id);
        String location = model.getLocation(id);
        double temp = model.getTemperature(id);
        double humid = model.getHumidity(id);

        Label lblLocation = new Label("Ort: " + location);
        lblLocation.setFont(new Font(17));

        Label lblHumid = new Label("Luftfeuchtigkeit: " + humid + "%");
        lblHumid.setFont(new Font(17));

        Label lblTemp = new Label("Temperatur: " + temp + "°C");
        lblTemp.setFont(new Font(17));

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5, 0, 5, 0));
        flowPane.setVgap(4);
        flowPane.setHgap(8);
        flowPane.setPrefWrapLength(250);
        flowPane.setStyle("-fx-background-color: DAE6F3;");
        flowPane.getChildren().addAll(Arrays.asList(lblLocation, lblTemp, lblHumid));

        borderPane.setTop(addTitleBar());
        borderPane.setBottom(addExitButton());
        borderPane.setLeft(addArduinoList());
        borderPane.setCenter(flowPane);
    }

    private HBox addTitleBar() {
        Label title = new Label("Projektwoche");
        title.setFont(new Font(26));
        title.setTextFill(Color.color(1, 1, 1));

        HBox hBox = new HBox(title);
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(7.5, 6, 7.5, 6));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");
        return hBox;
    }

    private VBox addExitButton() {
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(ae -> {
            try {
                controller.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        exitButton.setMaxSize(325, 200);
        exitButton.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(exitButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);
        return vBox;
    }

    private VBox addArduinoList() {
        Label arduinos = new Label("Arduinos");
        arduinos.setFont(new Font(18));


        VBox vBox = new VBox(arduinos);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);

        for (int i = 0; i < 5; i++) {
            VBox.setMargin(buttons[i], new Insets(0, 0, 0, 8));
            vBox.getChildren().add(buttons[i]);
            final int id = i;
            buttons[i].setOnAction(ae -> controller.changeArduino(id));
        }
        return vBox;
    }

    @Override
    public void update() {
    }

    public Parent getView() {
        return borderPane;
    }
}
