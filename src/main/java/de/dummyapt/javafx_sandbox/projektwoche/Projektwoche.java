package de.dummyapt.javafx_sandbox.projektwoche;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public final class Projektwoche extends Application {
    private final BorderPane borderPane = new BorderPane();
    private double xOffset = 0;
    private double yOffset = 0;
    private Connection connection;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        borderPane.setTop(addHBox());
        borderPane.setLeft(addVBox());
        borderPane.setCenter(addFlowPane());
        borderPane.setBottom(addVBoxBottom());
        borderPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        borderPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        borderPane.setStyle("""
                -fx-background-size: 1200 900;
                -fx-background-radius: 30;
                -fx-border-radius: 30;
                -fx-border-width:5;
                -fx-border-color: #FC3D44;
                -fx-font-size: 16pt;
                -fx-font-family: "Arial";
                -fx-base: rgb(132, 145, 47);
                -fx-background: rgb(29, 39, 57);""");

        var scene = new Scene(borderPane);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(-1);
    }

    private HBox addHBox() {
        var title = new Label("Projektwoche");
        title.setFont(new Font(26));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-base: rgb(0, 0, 0);");

        var hBox = new HBox();
        hBox.setPadding(new Insets(7.5, 6, 7.5, 6));
        hBox.setSpacing(10);
        hBox.setStyle("""
                -fx-background: rgb(0, 0, 0);
                -fx-background-color: rgb(50, 68, 75);
                -fx-background-radius: 90;
                -fx-border-radius: 30;""");
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(title);
        return hBox;
    }

    private VBox addVBox() {
        var arduinos = new Label("Arduinos");
        arduinos.setFont(new Font(24));
        arduinos.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-base: rgb(0, 0, 0);");

        var vBox = new VBox(arduinos);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);

        var buttons = new Button[5];
        for (int i = 0; i < 5; i++) {
            buttons[i] = new Button("Arduino " + (i + 1));
            buttons[i].setStyle("""
                    -fx-text-fill: rgb(255, 255, 255);
                        -fx-base: rgb(162, 0, 37);
                        -fx-border-color: rgb(240, 163, 10);
                        -fx-border-radius: 5;
                        -fx-padding: 3 6 6 6;""");
            VBox.setMargin(buttons[i], new Insets(0, 0, 0, 8));
            vBox.getChildren().add(buttons[i]);
            final int id = i;
            buttons[i].setOnAction(ae -> {
                var location = getArduinos().get(id).location();
                var temperature = getArduinos().get(id).temperature();
                var humidity = getArduinos().get(id).humidity();

                var lblLocation = new Label("Location:  " + location);
                lblLocation.setFont(new Font(19));

                var lblTemp = new Label("Temperature: " + temperature + "°C");
                lblTemp.setFont(new Font(19));

                var lblHumid = new Label("Humidity: " + humidity + "%");
                lblHumid.setFont(new Font(19));

                var flowPane = new FlowPane();
                flowPane.setPadding(new Insets(5, 0, 5, 0));
                flowPane.setVgap(4);
                flowPane.setHgap(8);
                flowPane.setPrefWrapLength(250);
                flowPane.setStyle("""
                        -fx-background-size: 1200 900;
                        -fx-border-radius: 10 10 35 0;
                        -fx-background-radius: 10 10 35 0;
                        -fx-border-color: gold;
                        -fx-background-color: rgb(50,50,50);""".indent(1));
                flowPane.getChildren().addAll(Arrays.asList(lblLocation, lblTemp, lblHumid));
                borderPane.setCenter(flowPane);
            });
        }
        vBox.setPadding(new Insets(7.5, 18, 7.5, 6));
        return vBox;
    }

    private VBox addVBoxBottom() {
        var btnExit = new Button("_Exit");
        btnExit.setOnAction(e -> System.exit(0));
        btnExit.setMaxSize(325, 200);
        btnExit.setAlignment(Pos.CENTER);

        var vBox = new VBox();
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(8);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(btnExit);
        VBox.setMargin(btnExit, new Insets(0, 0, 0, 8));
        return vBox;
    }

    private FlowPane addFlowPane() {
        var label = new Label("Choose an Arduino");

        var flowPane = new FlowPane();
        flowPane.setPadding(new Insets(5, 0, 5, 0));
        flowPane.setVgap(4);
        flowPane.setHgap(8);
        flowPane.setPrefWrapLength(250);
        flowPane.setStyle("""
                -fx-background-size: 1200 900;
                -fx-border-radius: 10 10 35 0;
                -fx-background-radius: 10 10 35 0;
                -fx-border-color: gold;-fx-background-color: rgb(50,50,50);""");
        flowPane.setAlignment(Pos.TOP_CENTER);
        flowPane.getChildren().add(label);
        return flowPane;
    }

    private ObservableList<ArduinoData> getArduinos() {
        ObservableList<ArduinoData> arduinos = FXCollections.observableArrayList();
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM projektwoche.v_monitoring;");
            while (resultSet.next())
                arduinos.add(
                        new ArduinoData(
                                resultSet.getInt("id"),
                                resultSet.getString("location"),
                                resultSet.getDouble("temperature"),
                                resultSet.getDouble("humidity")
                        )
                );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arduinos;
    }
}
