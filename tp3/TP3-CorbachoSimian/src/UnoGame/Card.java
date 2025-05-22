package UnoGame;

public abstract class Card {
    protected String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // Método abstracto para verificar si la carta puede ser jugada sobre otra
    public abstract boolean canBePlayedOn(Card topCard);

    // Nuevo método abstracto para aplicar el efecto de la carta
    public abstract void applyEffect(Game game, Player player);
}
