package UnoGame;

public class PlusTwoCard extends Card {
    public PlusTwoCard(String color) {
        super(color);
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.acceptsPlayedCard(this);
    }

    @Override
    protected boolean acceptsPlayedCard(NumberCard playedCard) {
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(SkipCard playedCard) {
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(ReverseCard playedCard) {
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(PlusTwoCard playedCard) {
        return true;
    }

    @Override
    protected boolean acceptsPlayedCard(WildCard playedCard) {
        return true;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        game.getOrchestrator().next(); 
        Player targetPlayer = game.getOrchestrator().getCurrent();
        targetPlayer.giveCard(game.drawCard());
        targetPlayer.giveCard(game.drawCard());
        game.getOrchestrator().next();
    }
}
