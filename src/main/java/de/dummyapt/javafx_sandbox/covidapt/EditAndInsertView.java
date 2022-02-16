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
    private final Button runButton = new Button();


    public EditAndInsertView() {
        clearInputs();

        runButton.setText("Submit");
        runButton.setOnAction(ae -> {
            if (statusLabel.getText().equals("New entry added!") || statusLabel.getText().equals("Canceled!"))
                statusLabel.setText("");

            if (lkIdInput.getText().equals("") ||
                    lkNameInput.getText().equals("") ||
                    valueInput.getText().equals("")) {
                statusLabel.setText("Invalid input!");
                return;
            }

            try {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm your action");
                alert.setContentText("Do you want to add the entry now?");

                var result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.APPLY) {
                        var sql = "INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);";
                        var preparedStatement = Database.getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                        preparedStatement.setString(2, lkNameInput.getText());
                        preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                        preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                        preparedStatement.executeUpdate();
                        clearInputs();
                        statusLabel.setText("New entry added!");
                    } else {
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
        gridPane.add(runButton, 2, 4);
        gridPane.add(statusLabel, 2, 5);

        gridPane.setHgap(5);
        gridPane.setVgap(5);
    }

    public EditAndInsertView(Entry entry) {
        clearInputs();

        idInput.setEditable(false);
        idInput.setText(String.valueOf(entry.id()));
        lkIdInput.setText(String.valueOf(entry.lkId()));
        lkNameInput.setText(String.valueOf(entry.lkName()));
        valueInput.setText(String.valueOf(entry.value()));
        datePicker.valueProperty().setValue(entry.getDate().toLocalDate());

        runButton.setText("Update");
        runButton.setOnAction(ae -> {
            if (statusLabel.getText().equals("Entry updated") || statusLabel.getText().equals("Canceled!"))
                statusLabel.setText("");

            if (lkIdInput.getText().equals("") ||
                    lkNameInput.getText().equals("") ||
                    valueInput.getText().equals("")) {
                statusLabel.setText("Invalid input!");
                return;
            }

            try {
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Please confirm your action");
                alert.setContentText("Do you want to change the data row now?");

                var result = alert.showAndWait();
                if (result.isPresent()) {
                    if (result.get() == ButtonType.OK) {
                        var sql = "UPDATE inzidenzen SET lkid = ?, lkname = ?, inzidenz = ?, datum = ? WHERE id = ?;";
                        var preparedStatement = Database.getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                        preparedStatement.setString(2, lkNameInput.getText());
                        preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                        preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                        preparedStatement.setInt(5, Integer.parseInt(idInput.getText()));
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        clearInputs();
                        statusLabel.setText("Entry updated!");
                    } else {
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
        gridPane.add(runButton, 2, 5);
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
