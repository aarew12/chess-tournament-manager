package com.chess.tournament.domain.model;

import java.util.Objects;

public record TournamentPlayer(PlayerId id, String name, int rating) {

    public TournamentPlayer {
        Objects.requireNonNull(id, "Player ID cannot be null");
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (rating < 0) {
            throw new IllegalArgumentException("Player rating cannot be negative");
        }
    }
}
