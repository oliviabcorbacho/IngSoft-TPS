package Uno;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    class GameTest {

        // Caso 1: Test básico para verificar si una carta se puede jugar
        @Test
        void testCardPlayableSameColor() {
            Card redThree = new NumberCard("RED", 3);
            Card redFive = new NumberCard("RED", 5);

            assertTrue(redFive.isPlayableOn(redThree)); // Mismo color
        }

        @Test
        void testCardPlayableSameNumber() {
            Card redThree = new NumberCard("RED", 3);
            Card blueThree = new NumberCard("BLUE", 3);

            assertTrue(blueThree.isPlayableOn(redThree)); // Mismo número
        }

        @Test
        void testCardPlayableWild() {
            Card redThree = new NumberCard("RED", 3);
            Card wildCard = new WildCard();

            assertTrue(wildCard.isPlayableOn(redThree)); // WildCard siempre puede ser jugada
        }

        @Test
        void testCardNotPlayable() {
            Card redThree = new NumberCard("RED", 3);
            Card blueFive = new NumberCard("BLUE", 5);

            assertFalse(blueFive.isPlayableOn(redThree)); // Color y número diferentes
        }

        // Caso 2: Test para efectos de cartas especiales
        @Test
        void testDrawTwoEffect() {
            Game game = new Game();
            Player player1 = new Player("Player1");
            Player player2 = new Player("Player2");
            game.addPlayer(player1);
            game.addPlayer(player2);

            Card drawTwo = new DrawTwoCard("RED");
            game.setTopCard(drawTwo);

            int initialHandSize = player2.getHandSize();
            drawTwo.applyEffect(game);

            // Verificamos que el siguiente jugador robó 2 cartas
            assertEquals(initialHandSize + 2, player2.getHandSize());
            // Verificamos que se salta el siguiente turno
            assertEquals(player1, game.getCurrentPlayer());
        }

        @Test
        void testSkipEffect() {
            Game game = new Game();
            Player player1 = new Player("Player1");
            Player player2 = new Player("Player2");
            Player player3 = new Player("Player3");
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addPlayer(player3);

            Card skip = new SkipCard("BLUE");
            game.setTopCard(skip);

            skip.applyEffect(game);

            // Verificamos que el turno se salta correctamente
            assertEquals(player3, game.getCurrentPlayer());
        }

        @Test
        void testReverseEffect() {
            Game game = new Game();
            Player player1 = new Player("Player1");
            Player player2 = new Player("Player2");
            Player player3 = new Player("Player3");
            game.addPlayer(player1);
            game.addPlayer(player2);
            game.addPlayer(player3);

            Card reverse = new ReverseCard("GREEN");
            game.setTopCard(reverse);

            reverse.applyEffect(game);

            // Verificamos que el orden de los turnos se invirtió
            assertEquals(player1, game.getCurrentPlayer());
            game.advanceTurn();
            assertEquals(player3, game.getCurrentPlayer());
        }

        // Caso 3: Test del flujo completo de una partida
        @Test
        void testGameFlow() {
            Game game = new Game();
            Player player1 = new Player("Player1");
            Player player2 = new Player("Player2");
            Player player3 = new Player("Player3");

            List<Card> deck = new ArrayList<>();
            deck.add(new NumberCard("RED", 3));  // Para jugador 1
            deck.add(new NumberCard("BLUE", 3)); // Para jugador 2
            deck.add(new NumberCard("GREEN", 3)); // Para jugador 3
            deck.add(new WildCard());             // Wild card en el medio

            game.initialize(deck, List.of(player1, player2, player3));

            while (!game.isFinished()) {
                Player currentPlayer = game.getCurrentPlayer();
                Card topCard = game.getTopCard();

                // El jugador juega la primera carta válida
                Card playedCard = currentPlayer.playCard(topCard);
                if (playedCard != null) {
                    game.setTopCard(playedCard);
                    playedCard.applyEffect(game);
                } else {
                    currentPlayer.drawCards(1); // Robar si no puede jugar
                }

                game.advanceTurn();
            }

            // Verificamos que el juego termina cuando un jugador se queda sin cartas
            assertTrue(game.isFinished());
            assertNotNull(game.getWinner());
        }
    }

