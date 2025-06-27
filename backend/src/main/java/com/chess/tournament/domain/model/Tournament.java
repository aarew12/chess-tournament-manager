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

    public static Tournament create(String name, String description,
                                    LocalDateTime startDateTime) {
        return new Tournament(name, description, startDateTime);
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
