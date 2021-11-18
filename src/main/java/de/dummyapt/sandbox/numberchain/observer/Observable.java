package de.dummyapt.sandbox.numberchain.observer;

import de.dummyapt.sandbox.numberchain.Card;

public interface Observable {
    void registerObserver(Observer pObserver);

    void removeObserver(Observer pObserver);

    void notifyObserver();

    int getCurrentPlayer();

    String getStatusText();

    Card[][] getGameField();
}