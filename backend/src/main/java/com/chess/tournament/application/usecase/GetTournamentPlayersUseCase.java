package com.chess.tournament.application.usecase;

import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.model.TournamentPlayer;
import com.chess.tournament.domain.port.TournamentRepository;

import java.util.Collection;

public class GetTournamentPlayersUseCase {

    private final TournamentRepository tournamentRepository;

    public GetTournamentPlayersUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Collection<TournamentPlayer> execute(String tournamentIdString) {
        TournamentId tournamentId = TournamentId.from(tournamentIdString);
        var tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new IllegalArgumentException("Tournament not found"));

        return tournament.getRegisteredPlayers();
    }
}