package de.dummyapt.sandbox.mytableview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TableViewExample extends Application {
    private final ArrayList<State> states = new ArrayList<>();
    private final TableView<State> table = new TableView<>();
    private final TableColumn<State, String> name = new TableColumn<>("State");
    private final TableColumn<State, String> value = new TableColumn<>("Value");
    private final Scene scene = new Scene(table);

    @Override
    public void start(Stage stage) {
        addStatesToList();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        table.getColumns().add(name);
        table.getColumns().add(value);
        table.getItems().addAll(states);
        table.setPlaceholder(new Label("No data"));

        stage.setScene(scene);
        stage.show();
    }

    private void addStatesToList() {
        states.add(new State("Baden-Württemberg", 165.7));
        states.add(new State("Bayern", 228.4));
        states.add(new State("Berlin", 157.4));
        states.add(new State("Brandenburg", 134.1));
        states.add(new State("Bremen", 87.0));
        states.add(new State("Hamburg", 108.7));
        states.add(new State("Hessen", 126.3));
        states.add(new State("Mecklenburg-Vorpommern", 94.9));
        states.add(new State("Niedersachsen", 80.8));
        states.add(new State("Nordrhein-Westfalen", 96.2));
        states.add(new State("Rheinland-Pfalz", 89.1));
        states.add(new State("Saarland", 69.4));
        states.add(new State("Sachsen", 289.7));
        states.add(new State("Sachsen-Anhalt", 147.0));
        states.add(new State("Schleswig-Holstein", 71.3));
        states.add(new State("Thüringen", 338.2));
    }
}