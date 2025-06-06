package org.udesa.tp4corbachosimian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.udesa.tp4corbachosimian.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class Dealer {
    public List<Card> fullDeck(){
        ArrayList<Card> deck = new ArrayList<>();
        deck.addAll( cardsOn( "Red" ) ); //todas las rojas
        deck.addAll( cardsOn( "Blue" ) ); //todas las azules
        deck.addAll( cardsOn( "Yellow" ) ); //todas las amarillas
        deck.addAll( cardsOn( "Green" ) ); //todas las verdes
        // mezclar
        return deck;
    }

    private List<Card> cardsOn(String color) {
        return List.of( new WildCard( ),
                        new SkipCard( color ),
                        new Draw2Card( color ),
                        new ReverseCard( color ),
                        new NumberCard( color, 1),
                        new NumberCard( color, 2),
                        new NumberCard( color, 3),
                        new NumberCard( color, 4),
                        new NumberCard( color, 5),
                        new NumberCard( color, 6),
                        new NumberCard( color, 7),
                        new NumberCard( color, 8),
                        new NumberCard( color, 9));
    }
}

