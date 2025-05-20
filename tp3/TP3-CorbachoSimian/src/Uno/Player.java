package Uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Player {
    private String name;
    private List<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            // Aquí se añadirían cartas desde el mazo
            hand.add(Game.deck.remove(0)) //Simpondremos exception manager ideal
        }
    }

    public Card playCard(Card topCard) {
        // Busca una carta jugable en la mano
