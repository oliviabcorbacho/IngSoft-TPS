package UnoGame;

import java.util.*;

public class Game {
    private final List<Player> players = new ArrayList<>();
    private Orchestrator orchestrator;
    private Card topCard;
    public GameStatus gameStatus = new GameOn();
    public Player winner;

    public void addPlayer(Player player) {
        players.add(player);
        if (players.size() > 1) {
            Player prev = players.get(players.size() - 2);
            prev.next = player;
            player.prev = prev;
            player.next = players.getFirst();
            players.getFirst().prev = player;
        }
        ensureOrchestrator();
    }

    public void setTopCard(Card card) {
        this.topCard = card;
    }

    private void ensureOrchestrator() {
        if (orchestrator == null && !players.isEmpty() && players.size() > 1) {
            Orchestrator right = new RightOrchestrator(players.getFirst());
            Orchestrator left = new LeftOrchestrator(players.getFirst());
            right.setOther(left);
            left.setOther(right);
            orchestrator = right;
        }
    }

    public boolean canPlay(Player player, Card card) {
        ensureOrchestrator();
        return orchestrator.getCurrent() == player && card.canBePlayedOn(topCard);
    }

    public void playCard(Player player, Card card) {
        gameStatus.isOver();
        ensureOrchestrator();
        if (orchestrator.getCurrent() != player || !card.canBePlayedOn(topCard)) {
            player.giveCard(drawCard());
            player.giveCard(drawCard());
            return;
        }
        player.removeCard(card);
        topCard = card;
        card.applyEffect(this, player);
    }

    public void sayUno(Player player) {
        if (player.handSize() != 1) {
            player.giveCard(drawCard());
            player.giveCard(drawCard());
        }
    }

    public Player currentPlayer() {
        ensureOrchestrator();
        return orchestrator.getCurrent();
    }

    public void reverseOrchestrator() {
        ensureOrchestrator();
        orchestrator = orchestrator.reverse();
    }

    public boolean isDirectionReversed() {
        ensureOrchestrator();
        return orchestrator.isReversed();
    }

    public Card drawCard() {
        return new NumberCard("Red", 0);
    }

    public Orchestrator getOrchestrator() {
        ensureOrchestrator();
        return orchestrator;
    }

    public void endGame(Player winner) {
        this.gameStatus = new GameOver();
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }

    public Card getTopCard() {
        return topCard;
    }
}
