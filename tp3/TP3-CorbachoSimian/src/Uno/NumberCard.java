package Uno;

public class NumberCard extends Card {
    private int number;

    public NumberCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public void applyEffect(Game game) {
//        game.advanceTurn();
    }

    @Override
    public boolean isPlayableOn(Card topCard) {
        return super.isPlayableOn(topCard) ||
                (topCard instanceof NumberCard && ((NumberCard) topCard).number == this.number);
    }

}