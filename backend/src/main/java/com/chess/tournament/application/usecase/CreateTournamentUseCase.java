package com.chess.tournament.application.usecase;

import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.port.TournamentRepository;

public class CreateTournamentUseCase {

    private final TournamentRepository tournamentRepository;

    public CreateTournamentUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public String execute(CreateTournamentCommand command) {
        var tournament = Tournament.create(command.name(), command.description(), command.startDate(), command.type());

        tournamentRepository.save(tournament);

        return tournament.getId().value().toString();
    }
}
