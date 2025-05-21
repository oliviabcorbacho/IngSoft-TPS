package Uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> hand;
    public Player nextPlayer;
    public Player prevPlayer;

    public Player(String name, List<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public int getHandSize(){ return hand.size(); }

    public void drawCards(int amount){

    }

    public void setNextPlayer(Player nextPlayer){ this.nextPlayer = nextPlayer; }
    public void setPrevPlayer(Player prevPlayer){ this.prevPlayer = prevPlayer; }

}