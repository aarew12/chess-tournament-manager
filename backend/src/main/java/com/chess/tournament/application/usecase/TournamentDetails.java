package com.chess.tournament.application.usecase;

import com.chess.tournament.domain.model.TournamentStatus;
import com.chess.tournament.domain.model.TournamentType;

public record TournamentDetails(String id, String name, String description, TournamentType type, TournamentStatus status) {
}
