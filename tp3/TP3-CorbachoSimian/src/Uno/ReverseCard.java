package Uno;

public class ReverseCard extends Card {
    public ReverseCard(String color) {
        super(color, "REVERSE");
    }

    @Override
    public void applyEffect(Game game) {
        game.swapOrchestrator();
        game.orchestrator.next();
    }
}

