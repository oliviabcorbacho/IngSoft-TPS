package Uno;

import java.util.List;

public abstract class Orchestrator {
    private Orchestrator other;
    public Player current;

    public Orchestrator( Player current ){
        this.current = current;
    }

    public void setOther(Orchestrator other){ this.other = other; }

    public Orchestrator getOther(){ return other; }

    public abstract Player next();

    public abstract Player skip();
}
