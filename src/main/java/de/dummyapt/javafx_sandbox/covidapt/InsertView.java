package de.dummyapt.javafx_sandbox.covidapt;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.SQLException;

public final class InsertView {
    private final GridPane gridPane = new GridPane();

    public InsertView() {
        var lkIdInput = new TextField();
        var lkNameInput = new TextField();
        var valueInput = new TextField();
        var datePicker = new DatePicker();
        var statusLabel = new Label();

        var submitButton = new Button("Submit");
        submitButton.setOnAction(ae -> {
            if (addEntry(
                    Integer.parseInt(lkIdInput.getText()),
                    lkNameInput.getText(),
                    Double.parseDouble(valueInput.getText()),
                    Date.valueOf(datePicker.getValue())))
                statusLabel.setText("Success!");
            else statusLabel.setText("Error!");
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

        gridPane.setHgap(5);
        gridPane.setVgap(5);
    }

    private boolean addEntry(int lkId, String lkName, double value, Date date) {
        try {
            var preparedStatement = Database.getConnection().prepareStatement("INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);");
            preparedStatement.setString(1, String.valueOf(lkId));
            preparedStatement.setString(2, lkName);
            preparedStatement.setString(3, String.valueOf(value));
            preparedStatement.setString(4, String.valueOf(date));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Node getView() {
        return gridPane;
    }
}
