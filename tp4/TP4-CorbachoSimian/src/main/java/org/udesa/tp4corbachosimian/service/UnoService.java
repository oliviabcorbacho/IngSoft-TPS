package org.udesa.tp4corbachosimian.service;

import org.springframework.stereotype.Service;
import org.udesa.tp4corbachosimian.model.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UnoService {
    private final Map<UUID, Match> matches = new ConcurrentHashMap<>();
    
    public UUID createNewMatch(List<String> players) {
        UUID matchId = UUID.randomUUID();
        List<Card> deck = createFullDeck();
        Match match = Match.fullMatch(deck, players);
        matches.put(matchId, match);
        return matchId;
    }
    
    public void playCard(UUID matchId, String player, JsonCard jsonCard) {
        Match match = getMatch(matchId);
        Card card = convertJsonToCard(jsonCard);
        match.play(player, card);
    }
    
    public void drawCard(UUID matchId, String player) {
        Match match = getMatch(matchId);
        match.drawCard(player);
    }
    
    public JsonCard getActiveCard(UUID matchId) {
        Match match = getMatch(matchId);
        return match.activeCard().asJson();
    }
    
    public List<JsonCard> getPlayerHand(UUID matchId) {
        Match match = getMatch(matchId);
        return match.playerHand().stream()
                .map(card -> ((Card) card).asJson())
                .toList();
    }
    
    private Match getMatch(UUID matchId) {
        Match match = matches.get(matchId);
        if (match == null) {
            throw new RuntimeException("Match not found: " + matchId);
        }
        return match;
    }
    
    // Método para convertir JsonCard a Card (necesario por el bug en JsonCard.asCard())
    private Card convertJsonToCard(JsonCard jsonCard) {
        String type = jsonCard.getType();
        String color = jsonCard.getColor();
        Integer number = jsonCard.getNumber();
        boolean shout = jsonCard.isShout();
        
        Card card = switch (type) {
            case "NumberCard" -> new NumberCard(color, number);
            case "SkipCard" -> new SkipCard(color);
            case "ReverseCard" -> new ReverseCard(color);
            case "Draw2Card" -> new Draw2Card(color);
            case "WildCard" -> new WildCard().asColor(color);
            default -> throw new RuntimeException("Unknown card type: " + type);
        };
        
        return shout ? card.uno() : card;
    }
    
    private List<Card> createFullDeck() {
        List<Card> deck = new ArrayList<>();
        String[] colors = {"Red", "Blue", "Green", "Yellow"};
        
        // Números 0-9 para cada color
        for (String color : colors) {
            deck.add(new NumberCard(color, 0)); // Solo un 0 por color
            for (int i = 1; i <= 9; i++) {
                deck.add(new NumberCard(color, i));
                deck.add(new NumberCard(color, i)); // Dos de cada número 1-9
            }
            
            // Cartas especiales (2 de cada por color)
            for (int i = 0; i < 2; i++) {
                deck.add(new SkipCard(color));
                deck.add(new ReverseCard(color));
                deck.add(new Draw2Card(color));
            }
        }
        
        // Cartas comodín (4 de cada)
        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
        }
        
        Collections.shuffle(deck);
        return deck;
    }
}