package Uno;

public class SkipCard extends Card {
    public SkipCard(String color) {
        super(color, "SKIP");
    }

    @Override
    public void applyEffect(Game game) {
        game.orchestrator.skip();
    }
}

