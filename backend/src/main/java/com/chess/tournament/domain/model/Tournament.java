package com.chess.tournament.domain.model;

import java.time.LocalDateTime;

public class Tournament {

    private final String name;
    private final String description;
    private final LocalDateTime startDateTime;
    private TournamentStatus status;

    private Tournament(String name, String description, LocalDateTime startDateTime) {
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.status = TournamentStatus.PLANNED;
    }

    public static Tournament create(String name, String description, LocalDateTime startDateTime) {
        validateTournamentData(name, startDateTime);
        return new Tournament(name, description, startDateTime);
    }

    private static void validateTournamentData(String name, LocalDateTime startDateTime) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tournament name cannot be null or empty");
        }

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Tournament start date must be in the future");
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public TournamentStatus getStatus() {
        return status;
    }
}
