package de.dummyapt.sandbox.calculator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Calculator extends Application {
    private final GridPane gridPane = new GridPane();
    private final Button[][] btnNumbers = new Button[3][6];
    private final TextField txtResult = new TextField();
    private final Button btnClear = new Button("Clear");
    private double firstNumber = 0;
    private char operator = ' ';

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        txtResult.setEditable(false);

        double spacing = 5;
        gridPane.setHgap(spacing);
        gridPane.setVgap(spacing);

        int i = 1;
        for (int row = 2; row < 5; row++) {
            for (int column = 0; column < 3; column++) {
                btnNumbers[column][row] = new Button("" + i);
                gridPane.add(btnNumbers[column][row], column, row);
                final int tmpI = i;
                btnNumbers[column][row].setOnAction(ae -> txtResult.setText(txtResult.getText() + tmpI));
                i++;
            }
        }

        btnClear.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btnClear.setOnAction(ae -> txtResult.setText(""));

        Button btnMultiply = new Button("*");
        btnMultiply.setOnAction(ae -> {
            firstNumber = Double.parseDouble(txtResult.getText());
            txtResult.setText("");
            operator = '*';
        });

        Button btnDivide = new Button("/");
        btnDivide.setOnAction(ae -> {
            firstNumber = Double.parseDouble(txtResult.getText());
            txtResult.setText("");
            operator = '/';
        });

        Button btnAdd = new Button("+");
        btnAdd.setOnAction(ae -> {
            firstNumber = Double.parseDouble(txtResult.getText());
            txtResult.setText("");
            operator = '+';
        });

        Button btnSubtract = new Button("-");
        btnSubtract.setOnAction(ae -> {
            firstNumber = Double.parseDouble(txtResult.getText());
            txtResult.setText("");
            operator = '-';
        });

        Button btnEquals = new Button("=");
        btnEquals.setOnAction(ae -> {
            double secondNumber = Double.parseDouble(txtResult.getText());
            double result = 0;
            switch (operator) {
                case '*' -> result = firstNumber * secondNumber;
                case '/' -> result = firstNumber / secondNumber;
                case '-' -> result = firstNumber - secondNumber;
                case '+' -> result = firstNumber + secondNumber;
                default -> txtResult.setText("ERROR");
            }
            txtResult.setText("" + result);
        });

        Button btnPoint = new Button(".");
        btnPoint.setOnAction(ae -> {
            if (!txtResult.getText().contains(".")) {
                txtResult.setText(txtResult.getText() + ".");
            }
        });

        Button btnZero = new Button("0");
        btnZero.setOnAction(ae -> txtResult.setText(txtResult.getText() + "0"));

        btnZero.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        gridPane.add(btnMultiply, 3, 1);
        gridPane.add(btnDivide, 3, 2);
        gridPane.add(btnSubtract, 3, 3);
        gridPane.add(btnAdd, 3, 4);
        gridPane.add(btnEquals, 3, 5);
        gridPane.add(btnPoint, 2, 5);
        gridPane.add(btnZero, 0, 5, 2, 1);
        gridPane.add(txtResult, 0, 0, 4, 1);
        gridPane.add(btnClear, 0, 1, 3, 1);

        Scene scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.show();
    }
}