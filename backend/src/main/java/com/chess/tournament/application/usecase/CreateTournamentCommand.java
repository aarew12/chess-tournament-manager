package com.chess.tournament.application.usecase;

import com.chess.tournament.domain.model.TournamentType;

import java.time.LocalDate;

public record CreateTournamentCommand(String name, String description, LocalDate startDate, TournamentType type) {
}