package de.dummyapt.javafx_sandbox.exchangerapt.observer;

public interface Observable {
    void registerObserver(Observer pObserver);

    void notifyObserver();

    void removeObserver(Observer pObserver);

    int[] getCoins();
}
