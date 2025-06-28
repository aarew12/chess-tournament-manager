package com.chess.tournament.infrastructure.adapter.web;

public record CreateTournamentRequest(String name, String description, java.time.LocalDateTime startDateTime) {
}
