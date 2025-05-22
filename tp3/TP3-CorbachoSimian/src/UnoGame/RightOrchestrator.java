package UnoGame;

public class RightOrchestrator extends Orchestrator {
    public RightOrchestrator(Player first) {
        super(first);
    }

    @Override
    public void next() {
        current = current.next;
    }

    @Override
    public boolean isReversed() {
        return false;
    }
}
