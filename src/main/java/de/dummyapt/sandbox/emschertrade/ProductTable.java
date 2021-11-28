package de.dummyapt.sandbox.emschertrade;

import de.dummyapt.sandbox.emschertrade.database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Arrays;

public final class ProductTable extends Application {
    private final BorderPane borderPane = new BorderPane();
    private final TableView<Product> tableView = new TableView<>();
    private final TableColumn<Product, Integer> id = new TableColumn<>("ID");
    private final TableColumn<Product, String> name = new TableColumn<>("Name");
    private final TableColumn<Product, Double> price = new TableColumn<>("Price");
    private final TextField portInput = new TextField();
    private final TextField databaseInput = new TextField();
    private final TextField usernameInput = new TextField();
    private final PasswordField passwordInput = new PasswordField();
    private final TextField nameInput = new TextField();
    private final TextField priceInput = new TextField();
    private final Label statusLabel = new Label("Status:");
    private final Label statusText = new Label();
    private final Button connectButton = new Button("Connect");
    private final Button refreshButton = new Button("Refresh");
    private final Button insertButton = new Button("Insert");
    private final HBox hBoxServer = new HBox(portInput, databaseInput);
    private final HBox hBoxUser = new HBox(usernameInput, passwordInput);
    private final HBox hBoxStatus = new HBox(statusLabel, statusText);
    private final VBox vBox = new VBox(hBoxStatus, hBoxServer, hBoxUser,
            connectButton, refreshButton, nameInput, priceInput, insertButton);
    private final Scene scene = new Scene(borderPane);
    private Database repository;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws SQLException {
        var width = 75;
        borderPane.setLeft(vBox);
        borderPane.setCenter(tableView);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setMinWidth(width * 2.0);
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(Arrays.asList(id, name, price));
        tableView.setPlaceholder(new Label("No data"));

        portInput.setPromptText("Port");
        databaseInput.setPromptText("Database");
        usernameInput.setPromptText("Username");
        passwordInput.setPromptText("Password");
        nameInput.setPromptText("Name");
        priceInput.setPromptText("Price");

        portInput.setMaxWidth(width);
        databaseInput.setMaxWidth(width);
        usernameInput.setMaxWidth(width);
        passwordInput.setMaxWidth(width);
        statusLabel.setMinWidth(width);
        statusText.setMinWidth(width);

        connectButton.setMinWidth(width * 2.0);
        connectButton.setOnAction(ae -> {
            repository = new Database(portInput.getText(),
                    usernameInput.getText(),
                    passwordInput.getText(),
                    databaseInput.getText());
            try {
                repository.connect();
                statusText.setText("Connected");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                tableView.setItems(repository.getEntries());
                tableView.refresh();
            }
        });

        refreshButton.setMinWidth(width * 2.0);
        refreshButton.setOnAction(ae -> connectButton.fire());

        insertButton.setMinWidth(width * 2.0);
        insertButton.setOnAction(actionEvent -> {
            connectButton.fire();
            try {
                repository.createEntry(nameInput.getText(), Double.parseDouble(priceInput.getText()));
                statusText.setText("Entry created");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                refreshButton.fire();
            }
        });

        stage.setScene(scene);
        stage.setTitle("EmscherTrade");
        stage.setResizable(true);
        stage.show();
    }
}