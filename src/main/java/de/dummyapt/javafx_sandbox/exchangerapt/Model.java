package de.dummyapt.javafx_sandbox.exchangerapt;

import de.dummyapt.javafx_sandbox.exchangerapt.observer.Observable;
import de.dummyapt.javafx_sandbox.exchangerapt.observer.Observer;

import java.util.Arrays;
import java.util.LinkedList;

public class Model implements Observable {
    private final LinkedList<Observer> observerList;
    private final int[] coins;

    public Model() {
        observerList = new LinkedList<>();
        coins = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        reset();
    }

    @Override
    public void registerObserver(Observer pObserver) {
        observerList.add(pObserver);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observerList)
            observer.update();
    }

    @Override
    public void removeObserver(Observer pObserver) {
        observerList.remove(pObserver);
    }

    @Override
    public int[] getCoins() {
        return coins;
    }

    public void exchange(double total) {
        reset();
        if (total <= 0) return;
        while (total != 0)
            if (total >= 2) {
                coins[0]++;
                total = total - 2;
            } else if (total >= 1) {
                coins[1]++;
                total = total - 1;
            } else if (total >= 0.50) {
                coins[2]++;
                total = total - 0.5;
            } else if (total >= 0.20) {
                coins[3]++;
                total = total - 0.2;
            } else if (total >= 0.10) {
                coins[4]++;
                total = total - 0.1;
            } else if (total >= 0.05) {
                coins[5]++;
                total = total - 0.05;
            } else if (total >= 0.02) {
                coins[6]++;
                total = total - 0.02;
            } else if (total >= 0.00) {
                coins[7]++;
                total = 0.0;
            }
        notifyObserver();
    }

    public void reset() {
        Arrays.fill(coins, 0);
        notifyObserver();
    }
}
