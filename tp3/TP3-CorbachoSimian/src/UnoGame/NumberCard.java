package UnoGame;

public class NumberCard extends Card {
    private final int number;

    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.acceptsPlayedCard(this);
    }

    @Override
    protected boolean acceptsPlayedCard(NumberCard playedCard) {
        return playedCard.getColor().equals(this.color) || playedCard.getNumber() == this.number;
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
        return playedCard.getColor().equals(this.color);
    }

    @Override
    protected boolean acceptsPlayedCard(WildCard playedCard) {
        return true;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        game.getOrchestrator().next();
    }
}
