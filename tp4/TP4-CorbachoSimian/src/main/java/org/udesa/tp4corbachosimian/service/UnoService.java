package org.udesa.tp4corbachosimian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.tp4corbachosimian.model.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.UUID;

@Service
public class UnoService {
    @Autowired
    private Dealer dealer;
    private Map<UUID, Match> sessions = new HashMap<UUID, Match>();

    public UUID newMatch(List<String> players) {
        UUID newKey = UUID.randomUUID();
        sessions.put( newKey, Match.fullMatch( dealer.fullDeck(), players ));
        return newKey;
    }
}