package de.dummyapt.javafx_sandbox.connectfour;

import de.dummyapt.javafx_sandbox.connectfour.observer.Observable;
import de.dummyapt.javafx_sandbox.connectfour.observer.Observer;

import java.util.LinkedList;

public final class Model implements Observable {
    private final int[][] gameField;
    private final LinkedList<Observer> observerList;
    private int activePlayer;
    private String statusText = "Player 1 begins!";

    public Model() {
        gameField = new int[6][7];
        activePlayer = 1;
        observerList = new LinkedList<>();
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
        for (Observer observer : observerList)
            observer.update();
    }

    @Override
    public int getActivePlayer() {
        return activePlayer;
    }

    @Override
    public int[][] getGameField() {
        return gameField;
    }

    @Override
    public String getStatusText() {
        return statusText;
    }

    public void setTile(int pCol) {
        var rowNumber = -42;
        for (var row = 5; row >= 0; row--)
            if (gameField[row][pCol] == 0) {
                rowNumber = row;
                break;
            }

        if (rowNumber == -42) {
            statusText = "Impossible move!";
            notifyObserver();
            return;
        }

        gameField[rowNumber][pCol] = activePlayer;

        if (activePlayer != 0) {
            if (hasWon(rowNumber, pCol)) {
                statusText = "Spieler " + activePlayer + " gewinnt!";
                notifyObserver();
                activePlayer = 0;
                return;
            }
        } else
            return;

        for (var column = 0; column < 7; column++)
            if (gameField[0][column] == 0)
                break;
            else if (column == 6) {
                statusText = "Unentschieden.";
                notifyObserver();
                return;
            }

        if (activePlayer == 1) {
            activePlayer = 2;
            statusText = "Spieler 2 ist dran!";
        } else {
            activePlayer = 1;
            statusText = "Spieler 1 ist dran!";
        }
        notifyObserver();
    }

    private boolean hasWon(int pRow, int pCol) {
        // verticalCheck
        if (pRow <= 2)
            for (var i = 0; i < pRow; i++)
                if (gameField[pRow][pCol] == activePlayer && gameField[pRow + 1][pCol] == activePlayer &&
                        gameField[pRow + 2][pCol] == activePlayer && gameField[pRow + 3][pCol] == activePlayer)
                    return true;

        // horizontalCheck
        var inRowHor = 0;
        for (var i = 0; i < 7; i++)
            if (gameField[pRow][i] == activePlayer) {
                inRowHor++;
                if (inRowHor == 4) {
                    return true;
                }
            } else
                inRowHor = 0;

        // right to left diag -> DEBUG, PLEASE! Solution like vertical
        var rightToLeftDiag = 0;
        try {
            for (var i = -3; i <= 3; i++) {
                var row = pRow + i;
                var col = pCol + i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // left to right diag -> DEGUB, PLEASE! Solution like vertical
        var leftToRightDiag = 0;
        try {
            for (var i = -3; i <= 3; i++) {
                var row = pRow - i;
                var col = pCol + i;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void reset() {
        statusText = "Spieler 1 ist dran!";
        activePlayer = 1;

        for (var column = 0; column < 7; column++)
            for (var row = 0; row < 6; row++)
                gameField[row][column] = 0;

        notifyObserver();
    }
}