package com.chess.tournament.application.usecase;

import com.chess.tournament.application.exception.TournamentNotFoundException;
import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.port.TournamentRepository;

public class StartTournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public StartTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void execute(String tournamentIdString) {
        TournamentId tournamentId = TournamentId.from(tournamentIdString);
        var tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new TournamentNotFoundException(tournamentIdString));

        tournament.start();
        tournamentRepository.save(tournament);
    }
}
