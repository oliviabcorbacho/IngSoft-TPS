package UnoGame;

public class ReverseCard extends Card {
    public ReverseCard(String color) {
        super(color);
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.getColor().equals(this.color) || topCard instanceof ReverseCard;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        game.reverseOrchestrator();
        game.getOrchestrator().next();
    }
}
