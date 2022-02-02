package de.dummyapt.javafx_sandbox.covidapt;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.SQLException;

public final class InsertView {
    private final GridPane gridPane = new GridPane();
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    public InsertView() {
        var lkIdInput = new TextField();
        var lkNameInput = new TextField();
        var valueInput = new TextField();
        var datePicker = new DatePicker();
        var statusLabel = new Label();

        var submitButton = new Button("Submit");
        submitButton.setOnAction(ae -> {
            try {
                var preparedStatement = Database.getConnection().prepareStatement("INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);");
                preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                preparedStatement.setString(2, lkNameInput.getText());
                preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                preparedStatement.executeUpdate();
                statusLabel.setText("Success!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        gridPane.add(new Label("Location ID"), 1, 0);
        gridPane.add(lkIdInput, 2, 0);
        gridPane.add(new Label("Location"), 1, 1);
        gridPane.add(lkNameInput, 2, 1);
        gridPane.add(new Label("Value"), 1, 2);
        gridPane.add(valueInput, 2, 2);
        gridPane.add(new Label("Date"), 1, 3);
        gridPane.add(datePicker, 2, 3);
        gridPane.add(submitButton, 2, 4);
        gridPane.add(statusLabel, 2, 5);

        gridPane.setHgap(5);
        gridPane.setVgap(5);
    }

    public Node getView() {
        return gridPane;
    }
}
