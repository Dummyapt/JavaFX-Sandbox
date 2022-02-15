package de.dummyapt.javafx_sandbox.covidapt;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.SQLException;

public final class EditAndInsertView {
    private final Label statusLabel = new Label();
    private final GridPane gridPane = new GridPane();
    private final TextField idInput = new TextField();
    private final TextField lkIdInput = new TextField();
    private final TextField lkNameInput = new TextField();
    private final TextField valueInput = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final Alert alert = new Alert(Alert.AlertType.NONE);

    public EditAndInsertView() {
        clearInputs();

        var submitButton = new Button("Submit");
        submitButton.setOnAction(ae -> {
            if (statusLabel.getText().equals("New entry added!") || statusLabel.getText().equals("Canceled!"))
                statusLabel.setText("");

            if (lkIdInput.getText().equals("") ||
                    lkNameInput.getText().equals("") ||
                    valueInput.getText().equals("")) {
                statusLabel.setText("Invalid input!");
                return;
            }

            try {
                var sql = "INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);";
                var preparedStatement = Database.getConnection().prepareStatement(sql);
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

    public EditAndInsertView(Entry entry) {
        clearInputs();

        idInput.setEditable(false);
        idInput.setText(String.valueOf(entry.getId()));
        lkIdInput.setText(String.valueOf(entry.getLkId()));
        lkNameInput.setText(String.valueOf(entry.getLkName()));
        valueInput.setText(String.valueOf(entry.getValue()));
        datePicker.valueProperty().setValue(entry.getDate().toLocalDate());

        var updateButton = new Button("Update");
        updateButton.setOnAction(ae -> {
            if (statusLabel.getText().equals("Entry updated") || statusLabel.getText().equals("Canceled!"))
                statusLabel.setText("");

            if (lkIdInput.getText().equals("") ||
                    lkNameInput.getText().equals("") ||
                    valueInput.getText().equals("")) {
                statusLabel.setText("Invalid input!");
                return;
            }

            try {
                var sql = "UPDATE inzidenzen SET lkid = ?, lkname = ?, inzidenz = ?, datum = ? WHERE id = ?;";
                var preparedStatement = Database.getConnection().prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                preparedStatement.setString(2, lkNameInput.getText());
                preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                preparedStatement.setInt(5, Integer.parseInt(idInput.getText()));
                preparedStatement.executeUpdate();

                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm your action");
                alert.setContentText("Do you want to change the data row now?");

                var result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {
                        preparedStatement.executeUpdate();
                        clearInputs();
                        statusLabel.setText("Entry updated!");
                    } else {
                        preparedStatement.close();
                        clearInputs();
                        statusLabel.setText("Canceled!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        gridPane.add(new Label("ID"), 1, 0);
        gridPane.add(idInput, 2, 0);
        gridPane.add(new Label("Location ID"), 1, 1);
        gridPane.add(lkIdInput, 2, 1);
        gridPane.add(new Label("Location"), 1, 2);
        gridPane.add(lkNameInput, 2, 2);
        gridPane.add(new Label("Value"), 1, 3);
        gridPane.add(valueInput, 2, 3);
        gridPane.add(new Label("Date"), 1, 4);
        gridPane.add(datePicker, 2, 4);
        gridPane.add(updateButton, 2, 5);
        gridPane.add(statusLabel, 2, 6);

        gridPane.setHgap(5);
        gridPane.setVgap(5);
    }

    private void clearInputs() {
        idInput.clear();
        lkIdInput.clear();
        lkNameInput.clear();
        valueInput.clear();
        datePicker.getEditor().clear();
    }

    public Node getView() {
        return gridPane;
    }
}
