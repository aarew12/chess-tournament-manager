package com.chess.tournament.domain.service;

import com.chess.tournament.domain.model.Pairing;
import com.chess.tournament.domain.model.Tournament;

import java.util.List;

public interface PairingStrategy {

    List<Pairing> generatePairings(Tournament tournament, int round);
}