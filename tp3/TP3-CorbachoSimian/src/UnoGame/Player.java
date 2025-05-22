package UnoGame;

import java.util.LinkedList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand = new LinkedList<>();
    Player next;
    Player prev;

    public Player(String name) {
        this.name = name;
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public int handSize() {
        return hand.size();
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }
}
