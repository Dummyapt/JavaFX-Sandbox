package de.dummyapt.javafx_sandbox.exchangerapt;

import de.dummyapt.javafx_sandbox.exchangerapt.observer.Observable;
import de.dummyapt.javafx_sandbox.exchangerapt.observer.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class View implements Observer {
    private final BorderPane borderPane = new BorderPane();
    private final TextField txtInput = new TextField("0");
    private final Observable model;
    private final TextField txtTwoEuros = new TextField("0");
    private final TextField txtOneEuro = new TextField("0");
    private final TextField txtFiftyCents = new TextField("0");
    private final TextField txtTwentyCents = new TextField("0");
    private final TextField txtTenCents = new TextField("0");
    private final TextField txtFiveCents = new TextField("0");
    private final TextField txtTwoCents = new TextField("0");
    private final TextField txtOneCent = new TextField("0");

    public View(Controller controller, Model model) {
        this.model = model;

        final var btnExchange = new Button("_Exchange");
        btnExchange.setOnAction(ae -> {
            if (txtInput.getText().isEmpty() || txtInput.getText().isBlank()) txtInput.setText("0");
            else controller.exchange(Double.parseDouble(txtInput.getText()));
        });

        final var btnReset = new Button("_Reset");
        btnReset.setOnAction(ae -> controller.reset());

        final var btnClose = new Button("_Close");
        btnClose.setOnAction(ae -> System.exit(-1));

        final var hBox = new HBox();
        hBox.getChildren().addAll(btnExchange, btnReset, btnClose);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxWidth(375);
        hBox.setSpacing(10);

        txtInput.setMaxWidth(150);
        txtTwoEuros.setMaxWidth(50);
        txtOneEuro.setMaxWidth(50);
        txtFiftyCents.setMaxWidth(50);
        txtTwentyCents.setMaxWidth(50);
        txtTenCents.setMaxWidth(50);
        txtFiveCents.setMaxWidth(50);
        txtTwoCents.setMaxWidth(50);
        txtOneCent.setMaxWidth(50);

        txtTwoEuros.setEditable(false);
        txtOneEuro.setEditable(false);
        txtFiftyCents.setEditable(false);
        txtTwentyCents.setEditable(false);
        txtTenCents.setEditable(false);
        txtFiveCents.setEditable(false);
        txtTwoCents.setEditable(false);
        txtOneCent.setEditable(false);

        final var lblInput = new Label("Input");
        final var lblTwoEuros = new Label("2.00€");
        final var lblOneEuro = new Label("1.00€");
        final var lblFiftyCents = new Label("0.50€");
        final var lblTwentyCents = new Label("0.20€");
        final var lblTenCents = new Label("0.10€");
        final var lblFiveCents = new Label("0.05€");
        final var lblTwoCents = new Label("0.02€");
        final var lblOneCent = new Label("0.01€");

        final var gridPane = new GridPane();
        gridPane.add(txtInput, 1, 0, 4, 1);
        gridPane.add(lblInput, 0, 0);
        gridPane.add(lblTwoEuros, 0, 1);
        gridPane.add(lblOneEuro, 0, 2);
        gridPane.add(lblFiftyCents, 0, 3);
        gridPane.add(lblTwentyCents, 0, 4);
        gridPane.add(lblTenCents, 2, 1);
        gridPane.add(lblFiveCents, 2, 2);
        gridPane.add(lblTwoCents, 2, 3);
        gridPane.add(lblOneCent, 2, 4);
        gridPane.add(txtTwoEuros, 1, 1);
        gridPane.add(txtOneEuro, 1, 2);
        gridPane.add(txtFiftyCents, 1, 3);
        gridPane.add(txtTwentyCents, 1, 4);
        gridPane.add(txtTenCents, 3, 1);
        gridPane.add(txtFiveCents, 3, 2);
        gridPane.add(txtTwoCents, 3, 3);
        gridPane.add(txtOneCent, 3, 4);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        final var lblTitle = new Label("MoneyExchanger by Dummyapt");
        borderPane.setTop(lblTitle);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hBox);
        BorderPane.setAlignment(lblTitle, Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        BorderPane.setAlignment(hBox, Pos.CENTER);
        BorderPane.setMargin(lblTitle, new Insets(10, 0, 10, 0));
        BorderPane.setMargin(btnReset, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(hBox, new Insets(10, 0, 10, 0));
    }

    public Parent getView() {
        return borderPane;
    }

    @Override
    public void update() {
        int[] coins = model.getCoins();
        txtInput.setText("0");
        txtTwoEuros.setText(String.valueOf(coins[0]));
        txtOneEuro.setText(String.valueOf(coins[1]));
        txtFiftyCents.setText(String.valueOf(coins[2]));
        txtTwentyCents.setText(String.valueOf(coins[3]));
        txtTenCents.setText(String.valueOf(coins[4]));
        txtFiveCents.setText(String.valueOf(coins[5]));
        txtTwoCents.setText(String.valueOf(coins[6]));
        txtOneCent.setText(String.valueOf(coins[7]));
    }
}
