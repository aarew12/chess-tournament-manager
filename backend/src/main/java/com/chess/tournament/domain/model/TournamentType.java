package com.chess.tournament.domain.model;

public enum TournamentType {

    ROUND_ROBIN("Round Robin"),
    SINGLE_ELIMINATION("Single Elimination");

    private final String description;

    TournamentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}