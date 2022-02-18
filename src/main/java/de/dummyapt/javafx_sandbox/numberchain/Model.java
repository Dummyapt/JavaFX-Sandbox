package de.dummyapt.javafx_sandbox.numberchain;

import de.dummyapt.javafx_sandbox.numberchain.observer.Observable;
import de.dummyapt.javafx_sandbox.numberchain.observer.Observer;

import java.util.LinkedList;
import java.util.Random;

public final class Model implements Observable {
    public static final int FIELD_SIZE = 3;
    private final LinkedList<Observer> observerList;
    private final int[] countOfCards;
    private final Card[][] gameField;
    private final Random random = new Random();
    private int currentPlayer;
    private int nextNumber;
    private String statusText;

    public Model() {
        observerList = new LinkedList<>();
        gameField = new Card[FIELD_SIZE][FIELD_SIZE];
        countOfCards = new int[2];
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
        for (var observer : observerList)
            observer.update();
    }

    @Override
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Card[][] getGameField() {
        return gameField;
    }

    @Override
    public String getStatusText() {
        return statusText;
    }

    public void setCard(int column, int row) {
        if (currentPlayer == 0) return;
        else if (gameField[row][column].isRevealed()) {
            if (gameField[row][column].getBelongsToPlayer() == 3) {
                closeCard(row, column);
                changePlayer();
                notifyObserver();
                return;
            }
            statusText = "Already revealed!";
            notifyObserver();
            return;
        } else if (gameField[row][column].getNumber() == nextNumber) {
            revealCard(row, column);
            countMove();
        } else {
            wrongCard(row, column);
            notifyObserver();
            return;
        }
        notifyObserver();
    }

    private void revealCard(int row, int column) {
        gameField[row][column].setBelongsToPlayer(currentPlayer);
        gameField[row][column].setRevealed(true);
        statusText = String.format("Good!%nContinue, player %s.", currentPlayer);
    }

    private void wrongCard(int row, int column) {
        gameField[row][column].setBelongsToPlayer(3);
        gameField[row][column].setRevealed(true);
        statusText = String.format("Wrong card!%nClose the card.");
    }

    private void closeCard(int row, int column) {
        gameField[row][column].setBelongsToPlayer(0);
        gameField[row][column].setRevealed(false);
        statusText = "Wrong card was closed!";
    }

    private void changePlayer() {
        if (currentPlayer == 1) {
            currentPlayer++;
            statusText = String.format("Player %s's turn!", currentPlayer);
        } else if (currentPlayer == 2) {
            currentPlayer--;
            statusText = String.format("Player %s's turn!", currentPlayer);
        }
    }

    private void countMove() {
        countOfCards[currentPlayer - 1]++;
        nextNumber++;

        var counter = 0;
        for (var column = 0; column < FIELD_SIZE; column++)
            for (var row = 0; row < FIELD_SIZE; row++)
                if (gameField[column][row].isRevealed()) {
                    counter++;
                    if (counter == FIELD_SIZE * FIELD_SIZE) {
                        gameOver();
                        return;
                    }
                }
    }

    private void gameOver() {
        if (countOfCards[0] > countOfCards[1]) statusText = "Game over! Player 1 wins.";
        else statusText = "Game over! Player 2 wins.";
        currentPlayer = 0;
        notifyObserver();
    }

    public void reset() {
        countOfCards[0] = 0;
        countOfCards[1] = 0;
        currentPlayer = 1;
        nextNumber = 1;
        statusText = String.format("NumberChain%nPlayer %s begins!", currentPlayer);

        var counter = 1;
        for (var column = 0; column < FIELD_SIZE; column++)
            for (var row = 0; row < FIELD_SIZE; row++) {
                gameField[column][row] = new Card(counter);
                counter++;
            }

        for (var column = 0; column < FIELD_SIZE; column++)
            for (var row = 0; row < FIELD_SIZE; row++) {
                final var preColumn = random.nextInt(FIELD_SIZE);
                final var preRow = random.nextInt(FIELD_SIZE);
                final var memory = gameField[preColumn][preRow];
                gameField[preColumn][preRow] = gameField[column][row];
                gameField[column][row] = memory;
            }
        notifyObserver();
    }
}
