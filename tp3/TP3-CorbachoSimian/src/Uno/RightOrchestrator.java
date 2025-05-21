package Uno;

import java.util.List;

public class RightOrchestrator extends Orchestrator {
    public RightOrchestrator( Player current) {
        super( current );
    }

    public Player next (){
        return current.nextPlayer;
    }

    public Player skip(){
        return current.nextPlayer.nextPlayer;
    }

}
