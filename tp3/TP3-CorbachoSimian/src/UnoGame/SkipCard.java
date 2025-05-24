package UnoGame;

public class SkipCard extends Card {
    public SkipCard(String color) {
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
        return true;
    }

    @Override
    protected boolean acceptsPlayedCard(ReverseCard playedCard) {
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(PlusTwoCard playedCard) {
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(WildCard playedCard) {
        return true;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        game.getOrchestrator().next();
        game.getOrchestrator().next();
    }
}
