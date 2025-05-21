package Uno;

public class WildCard extends Card {
    public WildCard() {
        super("WILD", "WILD");
    }

    public void setColor(String color){ this.color = color; }

    public void applyEffect(Game game) {
        game.orchestrator.next();
    }

    public boolean isPlayableOn(Card topCard) {
        return true;
    }
}

