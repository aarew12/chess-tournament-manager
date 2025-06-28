package com.chess.tournament.infrastructure.adapter.web;

import com.chess.tournament.domain.model.TournamentType;

import java.time.LocalDate;

public record CreateTournamentRequest(String name, String description, LocalDate startDate, TournamentType type) {
}
