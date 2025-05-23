package UnoGame;

public class WildCard extends Card {
    private String chosenColor = "Wild"; // Default o hasta que se elija

    public WildCard() {
        super("Wild"); // El color base es "Wild", pero getColor() devuelve chosenColor
    }

    public void chooseColor(String color) {
        this.chosenColor = color;
    }

    @Override
    public String getColor() {
        // Devuelve el color elegido para propósitos de matching.
        // Si no se ha elegido, podría ser un problema para la lógica de `acceptsPlayedCard`
        // en otras cartas si esta WildCard es la `topCard`.
        // Asumimos que `chooseColor` se llama inmediatamente después de jugar una WildCard,
        // y antes de que otra carta intente jugarse sobre ella.
        return chosenColor;
    }

    @Override
    public boolean canBePlayedOn(Card topCard) {
        // Una WildCard siempre puede ser jugada (inicia el dispatch)
        return topCard.acceptsPlayedCard(this);
    }

    // Implementaciones para cuando WildCard es la topCard
    @Override
    protected boolean acceptsPlayedCard(NumberCard playedCard) {
        // Una NumberCard se puede jugar sobre esta WildCard (this/topCard)
        // si el color de la NumberCard coincide con el chosenColor de esta WildCard.
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(SkipCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(ReverseCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(PlusTwoCard playedCard) {
        return playedCard.getColor().equals(this.getColor());
    }

    @Override
    protected boolean acceptsPlayedCard(WildCard playedCard) {
        // Otra WildCard siempre se puede jugar sobre una WildCard.
        return true;
    }

    @Override
    public void applyEffect(Game game, Player player) {
        // Asumimos que la lógica para elegir color está fuera, o se llama aquí.
        // Por ahora, solo avanza el turno.
        // Si chooseColor no se ha llamado, getColor() podría devolver "Wild",
        // lo que haría que solo otras WildCards puedan jugarse encima.
        // Es crucial que chooseColor se establezca.
        game.getOrchestrator().next();
    }
}
