package UnoGame;

public abstract class Orchestrator {
    protected Player current;
    protected Orchestrator other;

    public Orchestrator(Player first) {
        this.current = first;
    }

    public void setOther(Orchestrator other) {
        this.other = other;
    }

    public Player getCurrent() {
        return current;
    }

    public abstract void next();

    public Orchestrator reverse() {
        return other;
    }

    public abstract boolean isReversed();
}
