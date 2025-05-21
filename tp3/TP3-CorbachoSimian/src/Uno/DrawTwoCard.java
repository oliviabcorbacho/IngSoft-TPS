package Uno;

public class DrawTwoCard extends Card {

    public DrawTwoCard(String color) {
        super(color, "DRAW_TWO");
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.orchestrator.next();
        nextPlayer.drawCards(2);
        game.orchestrator.skip();
    }
}

