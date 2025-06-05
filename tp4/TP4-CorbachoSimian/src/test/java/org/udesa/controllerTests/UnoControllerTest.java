package org.udesa.tp4corbachosimian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.tp4corbachosimian.model.JsonCard;
import org.udesa.tp4corbachosimian.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
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

    @Test
    public void testNewMatch() throws Exception {
        UUID testUuid = UUID.randomUUID();
        when(unoService.createNewMatch(any())).thenReturn(testUuid);

        mockMvc.perform(post("/newmatch")
                .param("players", "Alice")
                .param("players", "Bob"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + testUuid + "\""));
    }

    @Test
    public void testPlayCard() throws Exception {
        UUID matchId = UUID.randomUUID();
        JsonCard card = new JsonCard("Blue", 6, "NumberCard", false);

        mockMvc.perform(post("/play/" + matchId + "/Alice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(card)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDrawCard() throws Exception {
        UUID matchId = UUID.randomUUID();

        mockMvc.perform(post("/draw/" + matchId + "/Alice"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetActiveCard() throws Exception {
        UUID matchId = UUID.randomUUID();
        JsonCard activeCard = new JsonCard("Red", 1, "NumberCard", false);
        when(unoService.getActiveCard(matchId)).thenReturn(activeCard);

        mockMvc.perform(get("/activecard/" + matchId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.number").value(1));
    }

    @Test
    public void testGetPlayerHand() throws Exception {
        UUID matchId = UUID.randomUUID();
        List<JsonCard> hand = List.of(
                new JsonCard("Blue", 1, "NumberCard", false),
                new JsonCard("Red", 2, "NumberCard", false)
        );
        when(unoService.getPlayerHand(matchId)).thenReturn(hand);

        mockMvc.perform(get("/playerhand/" + matchId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].color").value("Blue"));
    }
}