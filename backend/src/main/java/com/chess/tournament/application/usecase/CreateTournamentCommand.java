package com.chess.tournament.application.usecase;

import java.time.LocalDateTime;

public record CreateTournamentCommand(String name, String description, LocalDateTime startDateTime) {
}