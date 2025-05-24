package UnoGame;

public class WildCard extends Card {
    private String chosenColor = "Wild";

    public WildCard() {
        super("Wild");
    }

    public void chooseColor(String color) {
        this.chosenColor = color;
    }

    @Override
    public String getColor() {
        return chosenColor;
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        return topCard.acceptsPlayedCard(this);
    }

    @Override
    protected boolean acceptsPlayedCard(NumberCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(SkipCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(ReverseCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(PlusTwoCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
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
