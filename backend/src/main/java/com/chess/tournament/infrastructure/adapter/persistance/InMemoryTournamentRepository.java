package com.chess.tournament.infrastructure.adapter.persistance;

import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentId;
import com.chess.tournament.domain.port.TournamentRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryTournamentRepository implements TournamentRepository {
    private final Map<TournamentId, Tournament> tournaments = new HashMap<>();

    @Override
    public Tournament save(Tournament tournament) {
        tournaments.put(tournament.getId(), tournament);
        return tournament;
    }

    @Override
    public Optional<Tournament> findById(TournamentId tournamentId) {
        return Optional.ofNullable(tournaments.get(tournamentId));
    }
}
