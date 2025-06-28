package com.chess.tournament.application.usecase;

import com.chess.tournament.application.exception.TournamentNotFoundException;
import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.port.TournamentRepository;

public class GetTournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public GetTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public TournamentDetails execute(String tournamentIdString) {
        TournamentId tournamentId = TournamentId.from(tournamentIdString);
        var tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new TournamentNotFoundException(tournamentIdString));
        return new TournamentDetails(tournament.getId().value().toString(), tournament.getName(), tournament.getDescription(), tournament.getType(), tournament.getStatus());
    }
}