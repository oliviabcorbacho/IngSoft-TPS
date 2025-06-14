//package org.udesa.tp4corbachosimian.model;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.udesa.tp4corbachosimian.controller.UnoController;
//import org.udesa.tp4corbachosimian.service.Dealer;
//import org.udesa.tp4corbachosimian.service.UnoService;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class UnoControllerTestJulio {
//    @Autowired
//    UnoController unoController;
//    @Autowired
//    MockMvc mockMvc;
//    @MockBean
//    Dealer dealer;
//
//    @BeforeEach
//    void beforeEach() {
//        when(dealer.fullDeck()).thenReturn(UnoServiceTest.fullDeck());
//    }
//
//    @Test void playWrongTurnTest () throws Throwable {
//        // crear un nuevo juego
//        String uuid = newGame();
//        assertNotNull(UUID.fromString(uuid));
//
//        // poner disponibles las cartas necesarias
//        List<JsonCard> cards = activeHand( uuid );
//
//        // probar que devuelve el texto del error, sin tener la aplicacion corrinedo
//        String resp = mockMvc.perform( post( "/play/" + uuid + "/Julio" )
//                .contentType( MediaType.APPLICATION_JSON )
//                .content( cards.getFirst().toString() ) )
//                .andDo( print() )
//                .andExpect( status().is( 500 ) ).andReturn().getResponse().getContentAsString();
//
//        // asertar que el mensaje es correcto
//
//        assertEquals( Player.NotPlayersTurn + "Julio", resp );
//    }
//
//    private List<JsonCard> activeHand(String uuid) throws Exception {
//        String resp = mockMvc.perform( get( "//playerhand/" + uuid ) )
//                .andExpect(status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        return new ObjectMapper().readValue(resp, new TypeReference<List<JsonCard>>() {});
//    }
//
//    private String newGame() throws Exception {
//        // simular la creacion con newMatch
//        String resp = mockMvc.perform( post("/newmatch?players=Emilio&players=Julio" ) )
//                .andExpect( status().isOk())
//                .andReturn().getResponse().getContentAsString();
//
//        return new ObjectMapper().readTree( resp ).asText();
//        // asertar el mensaje
//    }
//
//}
