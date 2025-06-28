package com.chess.tournament.domain.service;

import com.chess.tournament.domain.model.Pairing;
import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentPlayer;

import java.util.ArrayList;
import java.util.List;

public class SingleEliminationStrategy implements PairingStrategy {

    @Override
    public List<Pairing> generatePairings(Tournament tournament, int round) {

        //TODO works only for first round
        List<TournamentPlayer> players = new ArrayList<>(tournament.getRegisteredPlayers());
        List<Pairing> pairings = new ArrayList<>();

        int numPlayers = players.size();
        int bracketSize = calculateBracketSize(numPlayers);
        int numbByes = bracketSize - numPlayers;

        if(numPlayers <= 1) {
            return new ArrayList<>();
        }

        for (int i = 0; i < bracketSize / 2; i++) {
            if (i < numbByes) {
                var player = players.get(i);
                pairings.add(new Pairing(player.id(), null, player.name(), null));
            } else {
                var player1 = players.get(i);
                var player2 = players.get(bracketSize - i - 1);
                pairings.add(new Pairing(player1.id(), player2.id(), player1.name(), player2.name()));
            }
        }

        return pairings;
    }

    private int calculateBracketSize(int numberOfPlayers) {
        if (numberOfPlayers == Integer.highestOneBit(numberOfPlayers)) {
            return numberOfPlayers;
        }
        return (Integer.highestOneBit(numberOfPlayers) << 1);

    }
}
