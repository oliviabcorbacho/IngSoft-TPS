package Uno;

public class NumberCard extends Card {

    public NumberCard(String color, int number) {
        super(color, Integer.toString(number));
    }

    @Override
    public void applyEffect(Game game) {
        game.orchestrator.next();
    }

}