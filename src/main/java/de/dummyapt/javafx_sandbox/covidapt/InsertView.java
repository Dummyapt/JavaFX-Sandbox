package de.dummyapt.javafx_sandbox.covidapt;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public final class InsertView {
    private final GridPane gridPane = new GridPane();
    private final TextField lkIdInput = new TextField();
    private final TextField lkNameInput = new TextField();
    private final TextField valueInput = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Alert alert = new Alert(Alert.AlertType.NONE);


    public InsertView() {
        var statusLabel = new Label();

        var submitButton = new Button("Submit");
        submitButton.setOnAction(ae -> {
            if (statusLabel.getText().equals("New entry added!") || statusLabel.getText().equals("Canceled!"))
                statusLabel.setText("");
            if (lkIdInput.getText().equals("") ||
                    lkNameInput.getText().equals("") ||
                    valueInput.getText().equals(""))
                statusLabel.setText("Invalid input!");
            try {
                var preparedStatement = Database.getConnection().prepareStatement("INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);");
                preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                preparedStatement.setString(2, lkNameInput.getText());
                preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                preparedStatement.executeUpdate();

                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm your action");
                alert.setContentText("Do you want to add the entry now?");

                var result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.APPLY) {
                        preparedStatement.executeUpdate();
                        clearInputs();
                        statusLabel.setText("New entry added!");
                    } else {
                        preparedStatement.close();
                        clearInputs();
                        statusLabel.setText("Canceled!");
                    }
                }
            } catch (SQLException e) {
                e.getLocalizedMessage();
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

    private void clearInputs() {
        lkIdInput.clear();
        lkNameInput.clear();
        valueInput.clear();
        datePicker.getEditor().clear();
    }

    public Node getView() {
        return gridPane;
    }
}
