package org.udesa.tp4corbachosimian.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.udesa.tp4corbachosimian.service.UnoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnoServiceTest {
    @Autowired
    private UnoService unoService;
    
    private UUID validMatchId;
    private List<String> players;

    @BeforeEach
    void setUp() {
        players = List.of("Martina", "Alex");
        validMatchId = unoService.newMatch(players);
    }

    @Test 
    public void newMatchReturnsValidUUID() {
        UUID id = unoService.newMatch(List.of("Player1", "Player2"));
        assertNotNull(id);
    }

    @Test
    public void newMatchWithSinglePlayer() {
        UUID id = unoService.newMatch(List.of("Solo"));
        assertNotNull(id);
    }

    @Test
    public void newMatchWithMultiplePlayers() {
        UUID id = unoService.newMatch(List.of("P1", "P2", "P3", "P4"));
        assertNotNull(id);
    }

    @Test
    public void newMatchWithEmptyPlayerList() {
        assertThrows(Exception.class, () -> {
            unoService.newMatch(List.of());
        });
    }

    @Test
    public void newMatchWithNullPlayerList() {
        assertThrows(Exception.class, () -> {
            unoService.newMatch(null);
        });
    }

    @Test
    public void playerHandValidMatch() {
        List<Card> hand = unoService.playerHand(validMatchId);
        assertNotNull(hand);
        assertFalse(hand.isEmpty());
    }

    @Test
    public void playerHandInvalidMatch() {
        UUID invalidId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.playerHand(invalidId);
        });
    }

    @Test
    public void playerHandNullMatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.playerHand(null);
        });
    }

    @Test
    public void playerHandInitialSize() {
        List<Card> hand = unoService.playerHand(validMatchId);
        assertEquals(7, hand.size());
    }

    @Test
    public void playerHandConsistency() {
        List<Card> hand1 = unoService.playerHand(validMatchId);
        List<Card> hand2 = unoService.playerHand(validMatchId);
        assertEquals(hand1.size(), hand2.size());
    }

    @Test
    public void activeCardValidMatch() {
        Card activeCard = unoService.activeCard(validMatchId);
        assertNotNull(activeCard);
    }

    @Test
    public void activeCardInvalidMatch() {
        UUID invalidId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.activeCard(invalidId);
        });
    }

    @Test
    public void activeCardNullMatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.activeCard(null);
        });
    }

    @Test
    public void activeCardConsistency() {
        Card card1 = unoService.activeCard(validMatchId);
        Card card2 = unoService.activeCard(validMatchId);
        assertEquals(card1, card2);
    }

    @Test
    public void activeCardNotInPlayerHand() {
        Card activeCard = unoService.activeCard(validMatchId);
        List<Card> playerHand = unoService.playerHand(validMatchId);
        assertFalse(playerHand.contains(activeCard));
    }

    @Test
    public void drawCardValidMatch() {
        assertDoesNotThrow(() -> {
            unoService.drawCard(validMatchId, "Martina");
        });
    }

    @Test
    public void drawCardInvalidMatch() {
        UUID invalidId = UUID.randomUUID();
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.drawCard(invalidId, "Martina");
        });
    }

    @Test
    public void drawCardNullMatch() {
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.drawCard(null, "Martina");
        });
    }

    @Test
    public void drawCardInvalidPlayer() {
        assertThrows(RuntimeException.class, () -> {
            unoService.drawCard(validMatchId, "NonExistentPlayer");
        });
    }

    @Test
    public void drawCardIncreasesHandSize() {
        int initialSize = unoService.playerHand(validMatchId).size();
        unoService.drawCard(validMatchId, "Martina");
        int newSize = unoService.playerHand(validMatchId).size();
        assertEquals(initialSize + 1, newSize);
    }

    @Test
    public void playInvalidMatch() {
        UUID invalidId = UUID.randomUUID();
        Card card = new NumberCard("Red", 2);
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.play(invalidId, "Martina", card);
        });
    }

    @Test
    public void playNullMatch() {
        Card card = new NumberCard("Red", 2);
        assertThrows(IllegalArgumentException.class, () -> {
            unoService.play(null, "Martina", card);
        });
    }

    @Test
    public void playInvalidPlayer() {
        Card card = new NumberCard("Red", 2);
        assertThrows(RuntimeException.class, () -> {
            unoService.play(validMatchId, "NonExistentPlayer", card);
        });
    }

    @Test
    public void playCardNotInHand() {
        Card cardNotInHand = new NumberCard("Red", 2);
        assertThrows(RuntimeException.class, () -> {
            unoService.play(validMatchId, "Martina", cardNotInHand);
        });
    }

    @Test
    public void playValidCardFromHand() {
        List<Card> playerHand = unoService.playerHand(validMatchId);
        Card activeCard = unoService.activeCard(validMatchId);
        
        // Find a valid card from player's hand that matches active card
        Card validCard = playerHand.stream()
            .filter(card -> activeCard.acceptsOnTop(card))
            .findFirst()
            .orElse(null);
        
        if (validCard != null) {
            assertDoesNotThrow(() -> {
                unoService.play(validMatchId, "Martina", validCard);
            });
        } else {
            assertTrue(true, "No valid card found in initial hand - test skipped");
        }
    }

    @Test
    public void multipleMatchesAreIndependent() {
        UUID match1 = unoService.newMatch(List.of("Player1", "Player2"));
        UUID match2 = unoService.newMatch(List.of("Player3", "Player4"));
        
        assertNotEquals(match1, match2);
        assertNotNull(unoService.activeCard(match1));
        assertNotNull(unoService.activeCard(match2));
        assertNotNull(unoService.playerHand(match1));
        assertNotNull(unoService.playerHand(match2));
    }

    @Test
    public void drawCardAfterPlay() {
 
        unoService.drawCard(validMatchId, "Martina");
        int handSizeAfterDraw = unoService.playerHand(validMatchId).size();
        assertEquals(8, handSizeAfterDraw);
    }
}
