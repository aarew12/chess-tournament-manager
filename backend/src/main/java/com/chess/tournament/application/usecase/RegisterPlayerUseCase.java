package com.chess.tournament.application.usecase;

import com.chess.tournament.application.exception.TournamentNotFoundException;
import com.chess.tournament.domain.model.PlayerId;
import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.port.TournamentRepository;

public class RegisterPlayerUseCase {

    private final TournamentRepository tournamentRepository;

    public RegisterPlayerUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void execute(RegisterPlayerCommand command) {
        TournamentId tournamentId = TournamentId.from(command.tournamentId());
        Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new TournamentNotFoundException(command.tournamentId()));

        PlayerId playerId = PlayerId.generate();
        tournament.registerPlayer(playerId, command.playerName(), command.rating());

        tournamentRepository.save(tournament);
    }
}
