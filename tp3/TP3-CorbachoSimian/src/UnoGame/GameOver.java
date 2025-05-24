package UnoGame;

public class GameOver extends GameStatus {
    @Override
    public void isOver() {
        throw new RuntimeException("Game is over");
    }
}
