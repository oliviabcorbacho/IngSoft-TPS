package Uno;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    class GameTest {
        Game game;
        List<Player> players;
        Card reverseRed = new ReverseCard("RED");
        Card reverseBlue = new ReverseCard("BLUE");
        Card skipBlue = new SkipCard("BLUE");
        Card skipYellow = new SkipCard("YELLOW");
        Card drawTwoRed = new DrawTwoCard("RED");
        Card drawTwoGreen = new DrawTwoCard("BLUE");
        Card wild = new WildCard();
        Card oneRed = new NumberCard("RED", 1);
        Card oneBlue = new NumberCard("BLUE", 1);
        Card threeBlue = new NumberCard("BLUE", 3);

        List<Card> deck1 = List.of(reverseRed, oneBlue);

        List<Card> deck2 = List.of(wild, drawTwoGreen);

        List<Card> deck3 = List.of(threeBlue, skipYellow);

        List<Card> deck4 = List.of(reverseBlue, drawTwoRed);


        @BeforeEach
        void setUp() {
            players = new ArrayList<Player>();
            players.add(new Player("Jugador1", deck1));
            players.add(new Player("Jugador2", deck2));
            players.add(new Player("Jugador3", deck3));
            players.add(new Player("Jugador4", deck4));

            // Configurar next y previous para cada jugador
            for (int i = 0; i < players.size(); i++) {
                Player currentPlayer = players.get(i);
                Player nextPlayer = players.get((i + 1) % players.size());
                Player prevPlayer = players.get((i - 1 + players.size()) % players.size());

                currentPlayer.setNextPlayer(nextPlayer);
                currentPlayer.setPrevPlayer(prevPlayer);
            }

            game = new Game(players.get(0));
        }


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

        // Tests para NumberCard
        @Test
        void testNumberCardEffect() {
            game.setFirstCard(oneRed);
            Player expectedNext = game.orchestrator.current.nextPlayer;
            oneRed.applyEffect(game);
            assertTrue(expectedNext.getName(), game.orchestrator.current.getName());
        }

//        // Tests para SkipCard
//        @Test
//        void testSkipCardEffect() {
//            game.setFirstCard(skipBlue);
//            Player expectedPlayer = game.current.nextPlayer.nextPlayer;
//            skipBlue.applyEffect(game);
//            assertEquals(expectedPlayer, game.current);
//        }
//
//        @Test
//        void testSkipCardPlayability() {
//            assertTrue(skipBlue.isPlayableOn(oneBlue)); // Mismo color
//            assertTrue(skipBlue.isPlayableOn(skipYellow)); // Misma acción
//            assertFalse(skipBlue.isPlayableOn(oneRed)); // Diferente color y acción
//        }
//
//        // Tests para DrawTwoCard
//        @Test
//        void testDrawTwoCardEffect() {
//            game.setFirstCard(drawTwoRed);
//            Player targetPlayer = game.current.nextPlayer;
//            int initialHandSize = targetPlayer.getHandSize();
//            drawTwoRed.applyEffect(game);
//            assertEquals(initialHandSize + 2, targetPlayer.getHandSize());
//            assertEquals(game.current, targetPlayer.nextPlayer);
//        }
//
//        @Test
//        void testDrawTwoCardPlayability() {
//            assertTrue(drawTwoRed.isPlayableOn(oneRed)); // Mismo color
//            assertTrue(drawTwoRed.isPlayableOn(drawTwoGreen)); // Misma acción
//            assertFalse(drawTwoRed.isPlayableOn(oneBlue)); // Diferente color y acción
//        }
//
//        // Tests para WildCard
//        @Test
//        void testWildCardEffect() {
//            game.setFirstCard(wild);
//            Player expectedNext = game.current.nextPlayer;
//            wild.applyEffect(game);
//            assertEquals(expectedNext, game.current);
//        }
//
//        @Test
//        void testWildCardColorChange() {
//            wild.setColor("RED");
//            assertEquals("RED", wild.getColor());
//        }
//
//        // Tests para el Game y Orchestrator
//        @Test
//        void testGameInitialization() {
//            assertNotNull(game.orchestrator);
//            assertNotNull(game.current);
//        }
//
//        @Test
//        void testOrchestratorSwap() {
//            Player initialNextPlayer = game.orchestrator.next();
//            game.swapOrchestrator();
//            Player newNextPlayer = game.orchestrator.next();
//            assertNotEquals(initialNextPlayer, newNextPlayer);
//        }
//
//        // Tests para Player
//        @Test
//        void testPlayerConnections() {
//            for (Player player : players) {
//                assertNotNull(player.nextPlayer);
//                assertNotNull(player.prevPlayer);
//                assertEquals(player, player.nextPlayer.prevPlayer);
//                assertEquals(player, player.prevPlayer.nextPlayer);
//            }
//        }
//
//        @Test
//        void testPlayerHandManagement() {
//            Player player = players.get(0);
//            int initialSize = player.getHandSize();
//            player.drawCards(2);
//            assertEquals(initialSize + 2, player.getHandSize());
//        }
//
//        // Tests para validación de jugadas
//        @Test
//        void testPlayValidation() {
//            // Probar jugada válida por color
//            game.setFirstCard(oneRed);
//            assertTrue(reverseRed.isPlayableOn(game.topCard));
//
//            // Probar jugada válida por número
//            game.setFirstCard(oneRed);
//            assertTrue(oneBlue.isPlayableOn(game.topCard));
//
//            // Probar jugada inválida
//            game.setFirstCard(oneRed);
//            assertFalse(threeBlue.isPlayableOn(game.topCard));
//        }
//
//        // Tests para secuencia de juego
//        @Test
//        void testGameFlow() {
//            game.setFirstCard(oneRed);
//            Player initialPlayer = game.current;
//
//            // Simular una ronda de juego
//            oneRed.applyEffect(game);
//            assertNotEquals(initialPlayer, game.current);
//
//            skipBlue.applyEffect(game);
//            assertNotEquals(initialPlayer.nextPlayer, game.current);
//        }
    }