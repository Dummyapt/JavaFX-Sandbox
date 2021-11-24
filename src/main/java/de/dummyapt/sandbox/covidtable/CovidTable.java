package de.dummyapt.sandbox.covidtable;

import de.dummyapt.sandbox.covidtable.database.Database;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

public final class CovidTable extends Application {
    private final TableView<Entry> tableView = new TableView<>();
    private final TableColumn<Entry, String> name = new TableColumn<>("Name");
    private final TableColumn<Entry, Double> value = new TableColumn<>("Value");
    private final TableColumn<Entry, Date> date = new TableColumn<>("Date");
    private final Scene scene = new Scene(tableView, 315, 400);
    private final Image icon = new Image("https://cdn.pixabay.com/photo/2020/04/29/08/24/coronavirus-5107804_960_720.png");

    @Override
    public void start(Stage stage) throws SQLException {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        date.setCellValueFactory(new PropertyValueFactory<>("date")u);

        tableView.getColumns().addAll(Arrays.asList(name, value, date));
        tableView.getItems().addAll(new Database().getEntries());
        tableView.setPlaceholder(new Label("No data"));

        stage.setScene(scene);
        stage.setTitle("Covid Table");
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.show();
    }
}