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
        return true;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        // No tiene efecto especial, solo avanza el turno
        game.getOrchestrator().next();
    }
}
