package UnoGame;

public class Orchestrator {
    private Player current;
    private boolean reversed = false;

    public Orchestrator(Player first) {
        this.current = first;
    }

    public Player getCurrent() {
        return current;
    }

    public void next() {
        current = reversed ? current.prev : current.next;
    }

    public void reverse() {
        reversed = !reversed;
    }

    public boolean isReversed() {
        return reversed;
    }
}
