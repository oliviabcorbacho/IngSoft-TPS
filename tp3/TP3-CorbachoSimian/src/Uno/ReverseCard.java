package Uno;

public class ReverseCard extends Card {
    public ReverseCard(String color) {
        super(color);
    }

    @Override
    public void applyEffect(Game game) {
        game.reverseDirection();
    }
}

