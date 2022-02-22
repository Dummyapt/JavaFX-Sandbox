package de.dummyapt.javafx_sandbox.covidapt;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Date;
import java.sql.SQLException;

public final class EditAndInsertView {
    private static final String ERROR_STYLE = """
            -fx-border-radius: 5px;
            -fx-border-color: red;""";
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
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please confirm your action");
            alert.setContentText("Do you want to add the entry now?");
            final var result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    lkIdInput.setStyle("");
                    if (!lkIdInput.getText().matches("^\\d+$"))
                        lkIdInput.setStyle(ERROR_STYLE);
                    lkNameInput.setStyle("");
                    if (!lkNameInput.getText().matches("^[a-zA-Z]+ [a-zA-Z]+$"))
                        lkNameInput.setStyle(ERROR_STYLE);
                    valueInput.setStyle("");
                    if (!valueInput.getText().matches("^\\d+.\\d+$"))
                        valueInput.setStyle(ERROR_STYLE);
                    datePicker.setStyle("");
                    if (!datePicker.getEditor().getText().matches("^\\d{1,2}.\\d{1,2}.\\d{4}$"))
                        datePicker.setStyle(ERROR_STYLE);

                    try {
                        final var sql = "INSERT INTO inzidenzen (lkid, lkname, inzidenz, datum) VALUES (?, ?, ?, ?);";
                        final var preparedStatement = Database.getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                        preparedStatement.setString(2, lkNameInput.getText());
                        preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                        preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    clearInputs();
                    statusLabel.setText("New entry added!");
                } else {
                    statusLabel.setText("Insertion canceled!");
                }
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
        idInput.setMouseTransparent(true);
        idInput.setFocusTraversable(false);
        idInput.setText(String.valueOf(entry.id()));
        lkIdInput.setText(String.valueOf(entry.lkId()));
        lkNameInput.setText(String.valueOf(entry.lkName()));
        valueInput.setText(String.valueOf(entry.value()));
        datePicker.valueProperty().setValue(entry.getDate().toLocalDate());
        datePicker.setEditable(false);

        runButton.setText("Update");
        runButton.setOnAction(ae -> {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Please confirm your action");
            alert.setContentText("Do you want to change the data row now?");
            final var result = alert.showAndWait();
            if (result.isPresent()) {
                if (result.get() == ButtonType.OK) {
                    lkIdInput.setStyle("");
                    if (!lkIdInput.getText().matches("^\\d+$"))
                        lkIdInput.setStyle(ERROR_STYLE);
                    lkNameInput.setStyle("");
                    if (!lkNameInput.getText().matches("^[a-zA-Z]+ [a-zA-Z]+$"))
                        lkNameInput.setStyle(ERROR_STYLE);
                    valueInput.setStyle("");
                    if (!valueInput.getText().matches("^\\d+.\\d+$"))
                        valueInput.setStyle(ERROR_STYLE);
                    datePicker.setStyle("");
                    if (!datePicker.getEditor().getText().matches("^\\d{1,2}.\\d{1,2}.\\d{4}$"))
                        datePicker.setStyle(ERROR_STYLE);
                    try {
                        final var sql = "UPDATE inzidenzen SET lkid = ?, lkname = ?, inzidenz = ?, datum = ? WHERE id = ?;";
                        final var preparedStatement = Database.getConnection().prepareStatement(sql);
                        preparedStatement.setInt(1, Integer.parseInt(lkIdInput.getText()));
                        preparedStatement.setString(2, lkNameInput.getText());
                        preparedStatement.setDouble(3, Double.parseDouble(valueInput.getText()));
                        preparedStatement.setDate(4, Date.valueOf(datePicker.getValue()));
                        preparedStatement.setInt(5, Integer.parseInt(idInput.getText()));
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    statusLabel.setText("Entry updated!");
                } else {
                    statusLabel.setText("Update canceled!");
                }
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
