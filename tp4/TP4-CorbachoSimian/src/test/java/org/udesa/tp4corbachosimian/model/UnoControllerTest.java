package org.udesa.tp4corbachosimian.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.tp4corbachosimian.controller.UnoController;
import org.udesa.tp4corbachosimian.service.UnoService;
import org.udesa.tp4corbachosimian.model.JsonCard;
import org.udesa.tp4corbachosimian.model.Card;
import org.udesa.tp4corbachosimian.model.NumberCard;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnoController.class)
public class UnoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UnoService unoService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private UUID validMatchId;
    private String validPlayer;

    @BeforeEach
    void setUp() {
        validMatchId = UUID.randomUUID();
        validPlayer = "TestPlayer";
    }

    // newMatch endpoint tests - POST /newmatch
    @Test
    public void newMatchReturnsUUID() throws Exception {
        UUID expectedId = UUID.randomUUID();
        when(unoService.newMatch(any(List.class))).thenReturn(expectedId);

        mockMvc.perform(post("/newmatch")
                .param("players", "Player1", "Player2"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + expectedId.toString() + "\""));
    }

    @Test
    public void newMatchWithSinglePlayer() throws Exception {
        UUID expectedId = UUID.randomUUID();
        when(unoService.newMatch(any(List.class))).thenReturn(expectedId);

        mockMvc.perform(post("/newmatch")
                .param("players", "Solo"))
                .andExpect(status().isOk());
    }

    @Test
    public void newMatchWithMultiplePlayers() throws Exception {
        UUID expectedId = UUID.randomUUID();
        when(unoService.newMatch(any(List.class))).thenReturn(expectedId);

        mockMvc.perform(post("/newmatch")
                .param("players", "P1", "P2", "P3", "P4"))
                .andExpect(status().isOk());
    }

    @Test
    public void newMatchWithNoPlayers() throws Exception {
        mockMvc.perform(post("/newmatch"))
                .andExpect(status().is(500));
    }

    // play endpoint tests - POST /play/{matchId}/{player}
    @Test
    public void playValidCard() throws Exception {
        JsonCard jsonCard = new JsonCard();
        jsonCard.color = "Red";
        jsonCard.number = 2;
        jsonCard.type = "NumberCard";
        jsonCard.shout = false;

        mockMvc.perform(post("/play/{matchId}/{player}", validMatchId, validPlayer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jsonCard)))
                .andExpect(status().isOk());

        verify(unoService).play(eq(validMatchId), eq(validPlayer), any(Card.class));
    }

    @Test
    public void playIllegalArgumentException() throws Exception {
        JsonCard jsonCard = new JsonCard();
        jsonCard.color = "Red";
        jsonCard.number = 2;
        jsonCard.type = "NumberCard";

        doThrow(new IllegalArgumentException("Invalid move")).when(unoService)
                .play(any(UUID.class), anyString(), any(Card.class));

        mockMvc.perform(post("/play/{matchId}/{player}", validMatchId, validPlayer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jsonCard)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid move"));
    }

    @Test
    public void playRuntimeException() throws Exception {
        JsonCard jsonCard = new JsonCard();
        jsonCard.color = "Red";
        jsonCard.number = 2;
        jsonCard.type = "NumberCard";

        doThrow(new RuntimeException("Server error")).when(unoService)
                .play(any(UUID.class), anyString(), any(Card.class));

        mockMvc.perform(post("/play/{matchId}/{player}", validMatchId, validPlayer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jsonCard)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Server error"));
    }

    @Test
    public void playInvalidJSON() throws Exception {
        mockMvc.perform(post("/play/{matchId}/{player}", validMatchId, validPlayer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("invalid json"))
                .andExpect(status().is(500));
    }

    @Test
    public void playMissingRequestBody() throws Exception {
        mockMvc.perform(post("/play/{matchId}/{player}", validMatchId, validPlayer)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(500));
    }

    // drawCard endpoint tests - POST /draw/{matchId}/{player}
    @Test
    public void drawCardSuccess() throws Exception {
        mockMvc.perform(post("/draw/{matchId}/{player}", validMatchId, validPlayer))
                .andExpect(status().isOk());

        verify(unoService).drawCard(validMatchId, validPlayer);
    }

    @Test
    public void drawCardIllegalArgumentException() throws Exception {
        doThrow(new IllegalArgumentException("Player not found")).when(unoService)
                .drawCard(any(UUID.class), anyString());

        mockMvc.perform(post("/draw/{matchId}/{player}", validMatchId, validPlayer))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Player not found"));
    }

    @Test
    public void drawCardInvalidUUID() throws Exception {
        mockMvc.perform(post("/draw/{matchId}/{player}", "invalid-uuid", validPlayer))
                .andExpect(status().is(500));
    }

    // activeCard endpoint tests - GET /activecard/{matchId}
    @Test
    public void activeCardSuccess() throws Exception {
        Card mockCard = new NumberCard("Red", 5);
        when(unoService.activeCard(validMatchId)).thenReturn(mockCard);

        mockMvc.perform(get("/activecard/{matchId}", validMatchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.number").value(5));
    }

    @Test
    public void activeCardInvalidUUID() throws Exception {
        mockMvc.perform(get("/activecard/{matchId}", "invalid-uuid"))
                .andExpect(status().is(500));
    }

    // playerHand endpoint tests - GET /playerhand/{matchId}
    @Test
    public void playerHandSuccess() throws Exception {
        List<Card> mockHand = List.of(
            new NumberCard("Red", 1),
            new NumberCard("Blue", 2)
        );
        when(unoService.playerHand(validMatchId)).thenReturn(mockHand);

        mockMvc.perform(get("/playerhand/{matchId}", validMatchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void playerHandEmptyHand() throws Exception {
        when(unoService.playerHand(validMatchId)).thenReturn(List.of());

        mockMvc.perform(get("/playerhand/{matchId}", validMatchId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void playerHandInvalidUUID() throws Exception {
        mockMvc.perform(get("/playerhand/{matchId}", "invalid-uuid"))
                .andExpect(status().is(500));
    }

    // HTTP method validation tests
    @Test
    public void wrongHttpMethodForNewMatch() throws Exception {
        mockMvc.perform(get("/newmatch"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void wrongHttpMethodForPlay() throws Exception {
        mockMvc.perform(get("/play/{matchId}/{player}", validMatchId, validPlayer))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void wrongHttpMethodForDraw() throws Exception {
        mockMvc.perform(get("/draw/{matchId}/{player}", validMatchId, validPlayer))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void wrongHttpMethodForActiveCard() throws Exception {
        mockMvc.perform(post("/activecard/{matchId}", validMatchId))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void wrongHttpMethodForPlayerHand() throws Exception {
        mockMvc.perform(post("/playerhand/{matchId}", validMatchId))
                .andExpect(status().isMethodNotAllowed());
    }

    // Path variable validation tests
    @Test
    public void playWithEmptyPlayer() throws Exception {
        JsonCard jsonCard = new JsonCard();
        jsonCard.color = "Red";
        jsonCard.number = 2;
        jsonCard.type = "NumberCard";

        mockMvc.perform(post("/play/{matchId}/", validMatchId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jsonCard)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void drawWithEmptyPlayer() throws Exception {
        mockMvc.perform(post("/draw/{matchId}/", validMatchId))
                .andExpect(status().isNotFound());
    }
}