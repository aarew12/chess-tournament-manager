package com.chess.tournament.domain.service;


import com.chess.tournament.domain.model.Pairing;
import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentPlayer;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements PairingStrategy {

    @Override
    public List<Pairing> generatePairings(Tournament tournament, int round) {

        //TODO works only for first round
        List<TournamentPlayer> players = new ArrayList<>(tournament.getRegisteredPlayers());
        List<Pairing> pairings = new ArrayList<>();

        int numPlayers = players.size();
        boolean isOddNumPlayers = numPlayers % 2 == 1;
        int numPairs = isOddNumPlayers ? (numPlayers + 1) / 2 : numPlayers / 2;

        if(numPlayers <= 1) {
            return new ArrayList<>();
        }

        for (int i = 0; i < numPairs; i++) {
            if (i == 0 && isOddNumPlayers) {
                var player = players.get(i);
                pairings.add(new Pairing(player.id(), null, player.name(), null));
            } else {
                var player1 = players.get(i);
                var player2 = players.get(numPlayers - i - 1);
                pairings.add(new Pairing(player1.id(), player2.id(), player1.name(), player2.name()));
            }

        }
        return pairings;
    }
}