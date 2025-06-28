package com.chess.tournament.application.usecase;

import com.chess.tournament.application.exception.TournamentNotFoundException;
import com.chess.tournament.domain.model.Pairing;
import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.port.TournamentRepository;
import com.chess.tournament.domain.service.PairingStrategy;
import com.chess.tournament.domain.service.RoundRobinStrategy;
import com.chess.tournament.domain.service.SingleEliminationStrategy;

import java.util.List;

public class GeneratePairingsUseCase {

    private final TournamentRepository tournamentRepository;

    public GeneratePairingsUseCase(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Pairing> execute(String tournamentIdString, int round) {
        TournamentId tournamentId = TournamentId.from(tournamentIdString);
        var tournament = tournamentRepository.findById(tournamentId).orElseThrow(() -> new TournamentNotFoundException(tournamentIdString));

        PairingStrategy strategy = switch (tournament.getType()) {
            case ROUND_ROBIN -> new RoundRobinStrategy();
            case SINGLE_ELIMINATION -> new SingleEliminationStrategy();
        };

        return strategy.generatePairings(tournament, round);
    }
}
