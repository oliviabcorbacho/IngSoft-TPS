package Uno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    public Orchestrator orchestrator;
    public List<Player> players;
    public Card topCard;
    public Player firstPlayer;

    public Game (Player firstPlayer) {
        this.firstPlayer = firstPlayer;
        this.orchestrator = createOrchestrators();
    }

    public void setFirstCard(Card card) {
        this.topCard = card;
        card.applyEffect(this);
    }

    private Orchestrator createOrchestrators(){
        Orchestrator base = new RightOrchestrator( firstPlayer );
        Orchestrator compliment = new LeftOrchestrtor( firstPlayer );
        base.setOther(compliment);
        compliment.setOther(base);

        return base;
    }

    public void swapOrchestrator(){
        this.orchestrator = this.orchestrator.getOther();
    }



}