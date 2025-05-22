package UnoGame;

public class SkipCard extends Card {
    public SkipCard(String color) {
        super(color);
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.getColor().equals(this.color) || topCard instanceof SkipCard;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        // Salta al siguiente jugador
        game.getOrchestrator().next();
        game.getOrchestrator().next();
    }
}
