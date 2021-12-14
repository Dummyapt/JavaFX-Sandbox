package de.dummyapt.javafx_sandbox.numberchain;

public final class Card {
    private final int number;
    private boolean revealed;
    private int belongsToPlayer;

    public Card(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public int getBelongsToPlayer() {
        return belongsToPlayer;
    }

    public void setBelongsToPlayer(int belongsToPlayer) {
        this.belongsToPlayer = belongsToPlayer;
    }
}