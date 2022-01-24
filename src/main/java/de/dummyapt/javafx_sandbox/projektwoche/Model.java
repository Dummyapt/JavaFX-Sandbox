package de.dummyapt.javafx_sandbox.projektwoche;

import de.dummyapt.javafx_sandbox.projektwoche.observer.Observable;
import de.dummyapt.javafx_sandbox.projektwoche.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Model implements Observable {
    private final LinkedList<Observer> observerList;
    private Connection connection;

    public Model() {
        observerList = new LinkedList<>();

        try {
            connection = Database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer pObserver) {
        observerList.add(pObserver);
    }

    @Override
    public void removeObserver(Observer pObserver) {
        observerList.remove(pObserver);
    }

    @Override
    public void notifyObserver() {
        for (var observer : observerList)
            observer.update();
    }

    @Override
    public int getId(int id) {
        return getArduinos().get(id).getId();
    }

    @Override
    public String getLocation(int id) {
        return getArduinos().get(id).getLocation();
    }

    @Override
    public double getTemperature(int id) {
        return getArduinos().get(id).getTemperature();
    }

    @Override
    public double getHumidity(int id) {
        return getArduinos().get(id).getHumidity();
    }

    // TODO: 24.01.2022 When this method is called, the GUI should show the corresponding arduino
    // TODO: 24.01.2022 Add SELECT privileges to java@localhost on arduino table
    // TODO: 24.01.2022 Create a view with the monitoring table showing joined Arduinos to get the location
    public void changeArduino(int id) {
        //  getArduinos().get(id).getId();

        notifyObserver();
    }

    private ObservableList<ArduinoData> getArduinos() {
        ObservableList<ArduinoData> arduinoData = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM projektwoche.v_monitoring;");
            while (resultSet.next())
                arduinoData.add(new ArduinoData(resultSet.getInt("id"), resultSet.getString("location"), resultSet.getDouble("temperature"), resultSet.getDouble("humidity")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arduinoData;
    }
}
