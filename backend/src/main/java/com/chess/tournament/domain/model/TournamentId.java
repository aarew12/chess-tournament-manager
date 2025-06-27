package com.chess.tournament.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TournamentId(UUID value) {

    public TournamentId {
        Objects.requireNonNull(value, "TournamentId cannot be null");
    }

    public static TournamentId generate() {
        return new TournamentId(UUID.randomUUID());
    }

    public static TournamentId from(String uuidString) {
        return new TournamentId(UUID.fromString(uuidString));
    }
}