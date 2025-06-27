package com.chess.tournament.application.usecase;

public record RegisterPlayerCommand(String tournamentId, String playerName, int rating) {

    public RegisterPlayerCommand {
        if (tournamentId == null || tournamentId.isBlank()) {
            throw new IllegalArgumentException("Tournament ID cannot be null or empty");
        }
        if (playerName == null || playerName.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (rating < 0) {
            throw new IllegalArgumentException("Rating cannot be negative");
        }
    }
}
