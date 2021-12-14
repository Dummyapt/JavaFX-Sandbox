package de.dummyapt.javafx_sandbox.connectfour.observer;

public interface Observable {
    void registerObserver(Observer pObserver);

    void notifyObserver();

    void removeObserver(Observer pObserver);

    int getActivePlayer();

    int[][] getGameField();

    String getStatusText();
}