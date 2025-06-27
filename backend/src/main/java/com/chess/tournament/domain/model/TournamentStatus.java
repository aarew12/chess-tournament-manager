package com.chess.tournament.domain.model;

public enum TournamentStatus {
    PLANNED("Tournament is planned and accepting registrations"),
    IN_PROGRESS("Tournament is currently running"),
    COMPLETED("Tournament has been completed"),
    CANCELLED("Tournament has been cancelled");

    private final String description;

    TournamentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
