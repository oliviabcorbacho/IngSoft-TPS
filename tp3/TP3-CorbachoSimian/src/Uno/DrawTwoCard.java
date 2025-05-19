package Uno;

public class DrawTwoCard extends Card {
    public DrawTwoCard(String color) {
        super(color);
    }

    @Override
    public void applyEffect(Game game) {
        game.getNextPlayer().drawCards(2);
        game.skipNextPlayer();
    }
}

