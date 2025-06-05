package org.udesa.tp4corbachosimian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.tp4corbachosimian.model.JsonCard;
import org.udesa.tp4corbachosimian.service.UnoService;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    
    @Autowired
    private UnoService unoService;
    
    @GetMapping("/")
    public String saludo() {
        return "index";
    }
    
    @PostMapping("newmatch")
    @ResponseBody
    public ResponseEntity<UUID> newMatch(@RequestParam List<String> players) {
        try {
            UUID matchId = unoService.createNewMatch(players);
            return ResponseEntity.ok(matchId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("play/{matchId}/{player}")
    @ResponseBody
    public ResponseEntity<Void> play(@PathVariable UUID matchId, 
                                   @PathVariable String player, 
                                   @RequestBody JsonCard card) {
        try {
            unoService.playCard(matchId, player, card);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("draw/{matchId}/{player}")
    @ResponseBody
    public ResponseEntity<Void> drawCard(@PathVariable UUID matchId, 
                                       @PathVariable String player) {
        try {
            unoService.drawCard(matchId, player);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("activecard/{matchId}")
    @ResponseBody
    public ResponseEntity<JsonCard> activeCard(@PathVariable UUID matchId) {
        try {
            JsonCard activeCard = unoService.getActiveCard(matchId);
            return ResponseEntity.ok(activeCard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("playerhand/{matchId}")
    @ResponseBody
    public ResponseEntity<List<JsonCard>> playerHand(@PathVariable UUID matchId) {
        try {
            List<JsonCard> hand = unoService.getPlayerHand(matchId);
            return ResponseEntity.ok(hand);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
