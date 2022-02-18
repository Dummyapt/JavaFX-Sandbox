package de.dummyapt.javafx_sandbox.tictactoe;

import de.dummyapt.javafx_sandbox.tictactoe.observer.Observable;
import de.dummyapt.javafx_sandbox.tictactoe.observer.Observer;

import java.util.LinkedList;

public final class Model implements Observable {
    private final int[][] gameField;
    private final LinkedList<Observer> observerList;
    private int activePlayer;
    private String statusText;

    public Model() {
        activePlayer = 1;
        gameField = new int[3][3];
        observerList = new LinkedList<>();
        reset();
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

    public void setTile(int pCol, int pRow) {
        if (gameOver()) return;
        if (gameField[pCol][pRow] == 0) gameField[pCol][pRow] = activePlayer;
        else {
            statusText = "Player " + gameField[pRow][pCol] + " already played in this field!";
            return;
        }

        if (hasWon()) {
            statusText = "Player " + activePlayer + " has won. Congratulations!";
            activePlayer = 0;
            notifyObserver();
            return;
        }

        int counter = -1;
        for (var col = 0; col < 3; col++)
            for (var row = 0; row < 3; row++)
                if (gameField[col][row] == 1 || gameField[col][row] == 2) {
                    counter++;
                    if (isDraw(counter)) {
                        statusText = "It's a draw!";
                        activePlayer = 0;
                        notifyObserver();
                        return;
                    }
                }
        changePlayer();
        notifyObserver();
    }

    private void changePlayer() {
        if (activePlayer == 1) {
            activePlayer++;
            statusText = "Player 2's turn!";
        } else if (activePlayer == 2) {
            activePlayer--;
            statusText = "Player 1's turn!";
        }
    }

    private boolean gameOver() {
        return activePlayer == 0;
    }

    public void reset() {
        statusText = "TicTacToe by Dummyapt";
        activePlayer = 1;
        for (var column = 0; column < 3; column++)
            for (var row = 0; row < 3; row++)
                gameField[row][column] = 0;
        notifyObserver();
    }

    private boolean isDraw(int counter) {
        return counter == 8;
    }

    private boolean hasWon() {
        return threeInRow() || threeInColumn() || threeDiagonal();
    }

    private boolean threeDiagonal() {
        return (gameField[0][0] == gameField[1][1] &&
                gameField[0][0] == gameField[2][2] &&
                gameField[0][0] == activePlayer) ||
                (gameField[0][2] == gameField[1][1] &&
                        gameField[0][2] == gameField[2][0] &&
                        gameField[0][2] == activePlayer);
    }

    private boolean threeInColumn() {
        return (gameField[0][0] == gameField[0][1] &&
                gameField[0][0] == gameField[0][2] &&
                gameField[0][0] == activePlayer) ||
                (gameField[1][0] == gameField[1][1] &&
                        gameField[1][0] == gameField[1][2] &&
                        gameField[1][0] == activePlayer) ||
                (gameField[2][0] == gameField[2][1] &&
                        gameField[2][0] == gameField[2][2] &&
                        gameField[2][0] == activePlayer);
    }

    private boolean threeInRow() {
        return (gameField[0][0] == gameField[1][0] &&
                gameField[0][0] == gameField[2][0] &&
                gameField[0][0] == activePlayer) ||
                (gameField[0][1] == gameField[1][1] &&
                        gameField[0][1] == gameField[2][1] &&
                        gameField[0][1] == activePlayer) ||
                (gameField[0][2] == gameField[1][2] &&
                        gameField[0][2] == gameField[2][2] &&
                        gameField[0][2] == activePlayer);
    }
}
