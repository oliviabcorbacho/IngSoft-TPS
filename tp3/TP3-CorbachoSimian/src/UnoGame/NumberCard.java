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
        return topCard instanceof NumberCard && ((NumberCard) topCard).number == this.number
                || topCard.getColor().equals(this.color);
    }

    @Override
    public void applyEffect(Game game, Player player) {
        // No tiene efecto especial, solo avanza el turno
        game.getOrchestrator().next();
    }
}
