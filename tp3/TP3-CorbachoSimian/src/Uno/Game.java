package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private List<Player> players = new ArrayList<>();
    private List<Card> deck;
    private Card topCard;
    private int currentPlayerIndex = 0;
    private boolean directionReversed = false;
    private boolean isFinished = false;
    private Player winner;

    // Inicialización del juego
    public void initialize(List<Card> deck, List<Player> players) {
        this.players = players;
        this.deck = new ArrayList<>(deck);

        // Repartir cartas iniciales (7 cartas por jugador)
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.drawCards(1);
            }
        }

        // Establecer la primera carta en la pila (descartes)
        this.topCard = this.deck.remove(0);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public Card getTopCard() {
        return topCard;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Player getNextPlayer() {
        return players.get(nextPlayerIndex());
    }

    public void advanceTurn() {
        if (directionReversed) {
            currentPlayerIndex = (currentPlayerIndex - 1 + players.size()) % players.size();
        } else {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }
    }

    public void skipNextPlayer() {
        advanceTurn();
    }

    public void reverseDirection() {
        directionReversed = !directionReversed;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Player getWinner() {
        return winner;
    }

    public void declareWinner(Player player) {
        isFinished = true;
        winner = player;
    }
}