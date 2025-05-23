package UnoGame;

public abstract class Card {
    protected String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean canBePlayedOn(Card topCard);

    protected abstract boolean acceptsPlayedCard(NumberCard playedCard);
    protected abstract boolean acceptsPlayedCard(SkipCard playedCard);
    protected abstract boolean acceptsPlayedCard(ReverseCard playedCard);
    protected abstract boolean acceptsPlayedCard(PlusTwoCard playedCard);
    protected abstract boolean acceptsPlayedCard(WildCard playedCard);

    public abstract void applyEffect(Game game, Player player);
}
