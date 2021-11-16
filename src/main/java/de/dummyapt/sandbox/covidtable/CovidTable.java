package de.dummyapt.sandbox.covidtable;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CovidTable extends Application {
    private final EntryDatabase repository = new EntryDatabase();
    private final TableView<Entry> table = new TableView<>();
    private final TableColumn<Entry, String> name = new TableColumn<>("Entry");
    private final TableColumn<Entry, String> value = new TableColumn<>("Value");
    private final Scene scene = new Scene(table);

    @Override
    public void start(Stage stage) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));

        table.getColumns().add(name);
        table.getColumns().add(value);
        table.getItems().addAll(repository.getEntries());
        table.setPlaceholder(new Label("No data"));

        stage.setScene(scene);
        stage.setTitle("Covid Table");
        stage.getIcons().add(new Image("https://cdn.pixabay.com/photo/2020/04/29/08/24/coronavirus-5107804_960_720.png"));
        stage.show();
    }
}