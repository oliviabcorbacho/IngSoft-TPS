package UnoGame;

public class PlusTwoCard extends Card {
    public PlusTwoCard(String color) {
        super(color);
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.getColor().equals(this.color) || topCard instanceof PlusTwoCard;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        Player next = game.getOrchestrator().getCurrent().next;
        next.giveCard(game.drawCard());
        next.giveCard(game.drawCard());
        game.getOrchestrator().next(); // skip next player
        game.getOrchestrator().next();
    }
}
