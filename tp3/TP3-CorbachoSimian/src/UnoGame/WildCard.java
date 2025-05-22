package UnoGame;

public class WildCard extends Card {
    private String chosenColor = null;

    public WildCard() {
        super("Wild");
    }

    public void chooseColor(String color) {
        this.chosenColor = color;
    }

    @Override
    public String getColor() {
        return chosenColor != null ? chosenColor : "Wild";
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
