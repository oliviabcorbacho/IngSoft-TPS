package Uno;

public class SkipCard extends Card {
    public SkipCard(String color) {
        super(color);
    }

    @Override
    public void applyEffect(Game game) {
        game.skipNextPlayer();
    }

}

