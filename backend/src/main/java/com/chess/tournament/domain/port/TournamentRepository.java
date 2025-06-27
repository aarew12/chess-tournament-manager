package com.chess.tournament.domain.port;

import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentId;

import java.util.Optional;

public interface TournamentRepository {

    Tournament save(Tournament tournament);

    Optional<Tournament> findById(TournamentId tournamentId);
}