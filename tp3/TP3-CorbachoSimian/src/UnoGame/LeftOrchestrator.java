package UnoGame;

public class LeftOrchestrator extends Orchestrator {
    public LeftOrchestrator(Player first) {
        super(first);
    }

    @Override
    public void next() {
        current = current.prev;
    }

    @Override
    public boolean isReversed() {
        return true;
    }
}
