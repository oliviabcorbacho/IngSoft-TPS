package Uno;

public class WildCard extends Card {
    public WildCard() {
        super("WILD");
    }

    @Override
    public void applyEffect(Game game) {
        // Determinísticamente elegimos el color más frecuente en la mano del jugador actual
        String chosen = game.getCurrentPlayer().mostFrequentColor();
        this.color = chosen;
        game.advanceTurn();
    }
}

