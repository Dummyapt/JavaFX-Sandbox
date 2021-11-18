package de.dummyapt.sandbox.moneyexchanger;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public final class MoneyExchanger extends Application {
    private final Label lblTitle = new Label("MoneyExchanger by Dummyapt");
    private final Label lblInput = new Label("Input");
    private final Label lblTwoEuros = new Label("2.00€");
    private final Label lblOneEuro = new Label("1.00€");
    private final Label lblFiftyCents = new Label("0.50€");
    private final Label lblTwentyCents = new Label("0.20€");
    private final Label lblTenCents = new Label("0.10€");
    private final Label lblFiveCents = new Label("0.05€");
    private final Label lblTwoCents = new Label("0.02€");
    private final Label lblOneCent = new Label("0.01€");
    private final Button btnExchange = new Button("_Exchange");
    private final Button btnReset = new Button("_Reset");
    private final Button btnClose = new Button("_Close");
    private final TextField txtInput = new TextField("0");
    private final TextField txtTwoEuros = new TextField("0");
    private final TextField txtOneEuro = new TextField("0");
    private final TextField txtFiftyCents = new TextField("0");
    private final TextField txtTwentyCents = new TextField("0");
    private final TextField txtTenCents = new TextField("0");
    private final TextField txtFiveCents = new TextField("0");
    private final TextField txtTwoCents = new TextField("0");
    private final TextField txtOneCent = new TextField("0");
    private final BorderPane borderPane = new BorderPane();
    private final GridPane gridPane = new GridPane();
    private final HBox hBox = new HBox();
    private int twoEuros;
    private int oneEuro;
    private int fiftyCents;
    private int twentyCents;
    private int tenCents;
    private int fiveCents;
    private int twoCents;
    private int oneCent;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        hBox.getChildren().addAll(btnExchange, btnReset, btnClose);
        hBox.setAlignment(Pos.CENTER);
        hBox.setMaxWidth(375);

        txtInput.setMaxWidth(150);

        txtTwoEuros.setMaxWidth(50);
        txtOneEuro.setMaxWidth(50);
        txtFiftyCents.setMaxWidth(50);
        txtTwentyCents.setMaxWidth(50);

        txtTenCents.setMaxWidth(50);
        txtFiveCents.setMaxWidth(50);
        txtTwoCents.setMaxWidth(50);
        txtOneCent.setMaxWidth(50);

        borderPane.setTop(lblTitle);
        borderPane.setCenter(gridPane);
        borderPane.setBottom(hBox);

        txtTwoEuros.setEditable(false);
        txtOneEuro.setEditable(false);
        txtFiftyCents.setEditable(false);
        txtTwentyCents.setEditable(false);
        txtTenCents.setEditable(false);
        txtFiveCents.setEditable(false);
        txtTwoCents.setEditable(false);
        txtOneCent.setEditable(false);

        BorderPane.setAlignment(lblTitle, Pos.CENTER);
        BorderPane.setAlignment(gridPane, Pos.CENTER);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        BorderPane.setMargin(lblTitle, new Insets(10, 0, 10, 0));
        BorderPane.setMargin(btnReset, new Insets(0, 10, 0, 10));
        BorderPane.setMargin(hBox, new Insets(10, 0, 10, 0));

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setVgap(10);
        gridPane.setHgap(10);
        hBox.setSpacing(10);

        gridPane.add(lblInput, 0, 0);
        gridPane.add(txtInput, 1, 0, 4, 1);

        gridPane.add(lblTwoEuros, 0, 1);
        gridPane.add(lblOneEuro, 0, 2);
        gridPane.add(lblFiftyCents, 0, 3);
        gridPane.add(lblTwentyCents, 0, 4);
        gridPane.add(txtTwoEuros, 1, 1);
        gridPane.add(txtOneEuro, 1, 2);
        gridPane.add(txtFiftyCents, 1, 3);
        gridPane.add(txtTwentyCents, 1, 4);

        gridPane.add(lblTenCents, 2, 1);
        gridPane.add(lblFiveCents, 2, 2);
        gridPane.add(lblTwoCents, 2, 3);
        gridPane.add(lblOneCent, 2, 4);
        gridPane.add(txtTenCents, 3, 1);
        gridPane.add(txtFiveCents, 3, 2);
        gridPane.add(txtTwoCents, 3, 3);
        gridPane.add(txtOneCent, 3, 4);

        btnExchange.setOnAction(ae -> {
            if (txtInput.getText().equals("")) {
                txtInput.setText("0");
                reset();
            } else if (!txtInput.getText().equals("")) {
                exchange();
                txtInput.setText("0");
            }
        });
        btnReset.setOnAction(ae -> reset());
        btnClose.setOnAction(ae -> System.exit(-1));

        var primaryScene = new Scene(borderPane);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setX(1_000);
        primaryStage.setY(300);
        primaryStage.setScene(primaryScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void exchange() {
        var total = Double.parseDouble(txtInput.getText());
        if (!zero()) {
            reset();
            while (total != 0) {
                if (total >= 2) {
                    twoEuros++;
                    total = total - 2;
                } else if (total >= 1) {
                    oneEuro++;
                    total = total - 1;
                } else if (total >= 0.50) {
                    fiftyCents++;
                    total = total - 0.5;
                } else if (total >= 0.20) {
                    twentyCents++;
                    total = total - 0.2;
                } else if (total >= 0.10) {
                    tenCents++;
                    total = total - 0.1;
                } else if (total >= 0.05) {
                    fiveCents++;
                    total = total - 0.05;
                } else if (total >= 0.02) {
                    twoCents++;
                    total = total - 0.02;
                } else if (total >= 0.00) {
                    oneCent++;
                    total = 0.0;
                }
            }
        } else
            reset();
        output();
    }

    private void reset() {
        twoEuros = 0;
        oneEuro = 0;
        fiftyCents = 0;
        twentyCents = 0;
        tenCents = 0;
        fiveCents = 0;
        twoCents = 0;
        oneCent = 0;

        txtInput.setText("0");
        txtTwoEuros.setText("0");
        txtOneEuro.setText("0");
        txtFiftyCents.setText("0");
        txtTwentyCents.setText("0");
        txtTenCents.setText("0");
        txtFiveCents.setText("0");
        txtTwoCents.setText("0");
        txtOneCent.setText("0");
    }

    private boolean zero() {
        return Double.parseDouble(txtInput.getText()) == 0;
    }

    private void output() {
        if (twoEuros != 0)
            txtTwoEuros.setText("" + twoEuros);
        if (oneEuro != 0)
            txtOneEuro.setText("" + oneEuro);
        if (fiftyCents != 0)
            txtFiftyCents.setText("" + fiftyCents);
        if (twentyCents != 0)
            txtTwentyCents.setText("" + twentyCents);
        if (tenCents != 0)
            txtTenCents.setText("" + tenCents);
        if (fiveCents != 0)
            txtFiveCents.setText("" + fiveCents);
        if (twoCents != 0)
            txtTwoCents.setText("" + twoCents);
        if (oneCent != 0)
            txtOneCent.setText("" + oneCent);
    }
}