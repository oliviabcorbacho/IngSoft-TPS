package Uno;

public class LeftOrchestrtor extends Orchestrator {
    public LeftOrchestrtor(Player current) {
        super(current);
    }

    public Player next (){
        return current.prevPlayer;
    }

    public Player skip(){
        return current.prevPlayer.prevPlayer;
    }
}
