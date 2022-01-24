package de.dummyapt.javafx_sandbox.projektwoche.observer;

public interface Observable {
    void registerObserver(Observer pObserver);

    void removeObserver(Observer pObserver);

    void notifyObserver();

    int getId(int id);

    String getLocation(int id);

    double getTemperature(int id);

    double getHumidity(int id);
}
