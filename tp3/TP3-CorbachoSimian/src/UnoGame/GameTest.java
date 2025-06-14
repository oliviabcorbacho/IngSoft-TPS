package UnoGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        game = new Game();
        player1 = new Player("Alice");
        player2 = new Player("Bob");
        player3 = new Player("Carol");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
    }

    @Test
    void testPlayerCanPlayMatchingNumberCard() {
        Card redFive = new NumberCard("Red", 5);
        Card blueFive = new NumberCard("Blue", 5);
        player1.giveCard(redFive);
        game.setTopCard(blueFive);

        assertTrue(game.canPlay(player1, redFive));
    }

    @Test
    void testPlayerCanPlayMatchingColorCard() {
        Card redFive = new NumberCard("Red", 5);
        Card redSeven = new NumberCard("Red", 7);
        player1.giveCard(redFive);
        game.setTopCard(redSeven);

        assertTrue(game.canPlay(player1, redFive));
    }

    @Test
    void testPlayerCanPlayWildCardAlways() {
        Card wild = new WildCard();
        Card blueThree = new NumberCard("Blue", 3);
        player1.giveCard(wild);
        game.setTopCard(blueThree);

        assertTrue(game.canPlay(player1, wild));
    }

    @Test
    void testPlayerCannotPlayNonMatchingCard() {
        Card redFive = new NumberCard("Red", 5);
        Card blueSeven = new NumberCard("Blue", 7);
        player1.giveCard(redFive);
        game.setTopCard(blueSeven);

        assertFalse(game.canPlay(player1, redFive));
    }

    @Test
    void testPlusTwoMakesNextPlayerDrawTwoAndSkip() {
        Card plusTwo = new PlusTwoCard("Red");
        Card redFive = new NumberCard("Red", 5);
        player1.giveCard(plusTwo);
        player2.giveCard(redFive);
        game.setTopCard(redFive);

        game.playCard(player1, plusTwo);

        assertEquals(3, player2.handSize());
        assertEquals(player3, game.currentPlayer());
    }

    @Test
    void testReverseChangesDirection() {
        Card reverse = new ReverseCard("Red");
        Card redFive = new NumberCard("Red", 5);
        player1.giveCard(reverse);
        game.setTopCard(redFive);

        game.playCard(player1, reverse);

        assertTrue(game.isDirectionReversed());
        assertEquals(player3.getName(), game.currentPlayer().getName());
    }

    @Test
    void testSkipSkipsNextPlayer() {
        Card skip = new SkipCard("Red");
        Card redFive = new NumberCard("Red", 5);
        player1.giveCard(skip);
        game.setTopCard(redFive);

        game.playCard(player1, skip);

        assertEquals(player3, game.currentPlayer());
    }

    @Test
    void testPlayerMustDrawIfPlaysOutOfTurn() {
        Card redFive = new NumberCard("Red", 5);
        Card blueFive = new NumberCard("Blue", 5);
        player2.giveCard(redFive);
        game.setTopCard(blueFive);

        game.playCard(player2, redFive);

        assertEquals(3, player2.handSize());
    }

    @Test
    void testPlayerMustDrawIfSaysUnoWithMoreThanOneCard() {
        Card redFive = new NumberCard("Red", 5);
        Card blueFive = new NumberCard("Blue", 5);
        player1.giveCard(redFive);
        player1.giveCard(blueFive);

        game.sayUno(player1);

        assertEquals(4, player1.handSize());
    }

    @Test
    void testWildCardAssignsColorAndCanBePlayedOnAnyCard() {
        Card wild = new WildCard();
        Card blueSeven = new NumberCard("Blue", 7);
        player1.giveCard(wild);
        game.setTopCard(blueSeven);

        assertTrue(game.canPlay(player1, wild));
        ((WildCard) wild).chooseColor("Green");
        assertEquals("Green", wild.getColor());
    }

    @Test
    void testPlusTwoStacking() {
        Card plusTwo1 = new PlusTwoCard("Red");
        Card plusTwo2 = new PlusTwoCard("Red");
        player1.giveCard(plusTwo1);
        player3.giveCard(plusTwo2);
        game.setTopCard(new NumberCard("Red", 5));

        game.playCard(player1, plusTwo1);
        assertTrue(game.canPlay(player3, plusTwo2));
    }

    @Test
    void testPlayerCannotPlayIfNoMatchingCard() {
        Card redFive = new NumberCard("Red", 5);
        Card blueSeven = new NumberCard("Blue", 7);
        player1.giveCard(blueSeven);
        game.setTopCard(redFive);

        assertFalse(game.canPlay(player1, blueSeven));
    }

    @Test
    void testPlayerDrawsIfTriesToPlayInvalidCard() {
        Card redFive = new NumberCard("Red", 5);
        Card blueSeven = new NumberCard("Blue", 7);
        player1.giveCard(blueSeven);
        game.setTopCard(redFive);

        int before = player1.handSize();
        game.playCard(player1, blueSeven);

        assertEquals(player1.handSize(), before + 2);
    }

    @Test
    void testSayUnoWithOneCardDoesNotDraw() {
        Card redFive = new NumberCard("Red", 5);
        player1.giveCard(redFive);

        game.sayUno(player1);

        assertEquals(1, player1.handSize());
    }

    @Test
    void testCircularPlayerOrder() {
        assertEquals(player1, player3.next);
        assertEquals(player3, player1.prev);
    }

    @Test
    void testFullRoundOfPlayWithThreePlayersAndNoWinner() {
        Card red1 = new NumberCard("Red", 1);
        Card red3_p1 = new NumberCard("Red", 3);
        Card yellow3_p1 = new NumberCard("Yellow", 3);
        Card blue3_p2 = new NumberCard("Blue", 3);
        Card green3_p2 = new NumberCard("Green", 3);
        Card blue5_p3 = new NumberCard("Blue", 5);
        Card yellow5_p3 = new NumberCard("Yellow", 5);

        player1.giveCard(red3_p1);
        player1.giveCard(yellow3_p1);
        player2.giveCard(blue3_p2);
        player2.giveCard(green3_p2);
        player3.giveCard(blue5_p3);
        player3.giveCard(yellow5_p3);
        game.setTopCard(red1);

        assertDoesNotThrow(() -> game.playCard(player1, red3_p1));
        assertEquals(player2, game.currentPlayer());
        assertNull(game.getWinner());

        assertDoesNotThrow(() -> game.playCard(player2, blue3_p2));
        assertEquals(player3, game.currentPlayer());
        assertNull(game.getWinner());

        assertDoesNotThrow(() -> game.playCard(player3, blue5_p3));
        assertEquals(player1, game.currentPlayer());
        assertNull(game.getWinner());
    }

    @Test
    void testGameEndsWhenPlayerPlaysLastCardAndFurtherPlaysAreBlocked() {
        Card red1 = new NumberCard("Red", 1);
        Card red3_p1_wins = new NumberCard("Red", 3);

        player1.giveCard(red3_p1_wins);
        player2.giveCard(new NumberCard("Blue", 1));
        game.setTopCard(red1);

        assertDoesNotThrow(() -> game.playCard(player1, red3_p1_wins));

        assertEquals(0, player1.handSize());
        game.endGame(player1);
        assertEquals(player1, game.getWinner());

        Player nextPlayerAfterWin = player2;
        Card bobCard = player2.getHand().getFirst();
        assertEquals(nextPlayerAfterWin, game.getOrchestrator().getCurrent());

        assertThrows(RuntimeException.class, () -> {game.playCard(nextPlayerAfterWin, bobCard);});
    }

    @Test
    void testActionsAfterExplicitEndGame() {
        game.endGame(player3);
        assertEquals(player3, game.getWinner());

        Card p1Card = new NumberCard("Red", 1);
        player1.giveCard(p1Card);
        game.setTopCard(new NumberCard("Red", 2));

        assertThrows(RuntimeException.class, () -> {game.playCard(player1, p1Card);});
    }
}