package com.chess.tournament.domain.model;

import java.util.Objects;
import java.util.UUID;

public record PlayerId(UUID value) {

    public PlayerId {
        Objects.requireNonNull(value, "PlayerId cannot be null");
    }

    public static PlayerId generate() {
        return new PlayerId(UUID.randomUUID());
    }

    public static PlayerId from(String uuidString) {
        return new PlayerId(UUID.fromString(uuidString));
    }
}