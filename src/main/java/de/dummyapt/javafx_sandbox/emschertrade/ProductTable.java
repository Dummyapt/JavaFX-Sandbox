package de.dummyapt.javafx_sandbox.emschertrade;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public final class ProductTable extends Application {
    private final Label statusText = new Label();
    private Connection connection;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws SQLException {
        var width = 75;
        var id = new TableColumn<Product, Integer>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        var name = new TableColumn<Product, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        var price = new TableColumn<Product, Double>("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        var tableView = new TableView<Product>();
        tableView.getColumns().addAll(Arrays.asList(id, name, price));
        tableView.setPlaceholder(new Label("No data"));

        var portInput = new TextField();
        portInput.setPromptText("Port");
        portInput.setMaxWidth(width);

        var databaseInput = new TextField();
        databaseInput.setPromptText("Database");
        databaseInput.setMaxWidth(width);

        var usernameInput = new TextField();
        usernameInput.setPromptText("Username");
        usernameInput.setMaxWidth(width);

        var passwordInput = new PasswordField();
        passwordInput.setPromptText("Password");
        passwordInput.setMaxWidth(width);

        var nameInput = new TextField();
        nameInput.setPromptText("Name");

        var priceInput = new TextField();
        priceInput.setPromptText("Price");

        var statusLabel = new Label("Status:");
        statusLabel.setMinWidth(width);

        statusText.setMinWidth(width);

        var connectButton = new Button("Connect");
        connectButton.setMinWidth(width * 2.0);
        connectButton.setOnAction(ae -> {
            try {
                connection = Database.getConnection(
                        portInput.getText(),
                        databaseInput.getText(),
                        usernameInput.getText(),
                        passwordInput.getText()
                );
                statusText.setText("Connected");
            } catch (SQLException e) {
                statusText.setText("Error");
                e.getLocalizedMessage();
            }
            tableView.setItems(getProducts());
        });

        var refreshButton = new Button("_Refresh");
        refreshButton.setMinWidth(width * 2.0);
        refreshButton.setOnAction(ae -> {
            if (connection == null)
                statusText.setText("Error");
            tableView.setItems(getProducts());
            tableView.refresh();
        });

        var insertButton = new Button("_Insert");
        insertButton.setMinWidth(width * 2.0);
        insertButton.setOnAction(actionEvent -> createProduct(name.getText(), Double.parseDouble(priceInput.getText())));

        var hBoxServer = new HBox(portInput, databaseInput);
        var hBoxUser = new HBox(usernameInput, passwordInput);
        var hBoxStatus = new HBox(statusLabel, statusText);
        var vBox = new VBox(hBoxStatus,
                hBoxServer,
                hBoxUser,
                connectButton,
                refreshButton,
                nameInput,
                priceInput,
                insertButton);

        var borderPane = new BorderPane();
        borderPane.setLeft(vBox);
        borderPane.setCenter(tableView);

        var scene = new Scene(borderPane);
        var icon = new Image("https://static.thenounproject.com/png/161182-200.png");
        stage.setTitle("EmscherTrade");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        try {
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("SELECT * FROM emschertrade.products");
            while (resultSet.next())
                products.add(
                        new Product(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("price")
                        )
                );
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }
        return products;
    }

    private void createProduct(String name, double price) {
        try {
            connection.createStatement().executeQuery(
                    String.format("INSERT INTO emschertrade.products (name, price) VALUES ('%s', %s)", name, price)
            );
        } catch (SQLException e) {
            e.getLocalizedMessage();
        }
    }
}