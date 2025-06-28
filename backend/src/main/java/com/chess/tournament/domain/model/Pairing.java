package com.chess.tournament.domain.model;

public record Pairing(PlayerId whitePlayer, PlayerId blackPlayer, String whitePlayerName, String blackPlayerName) {
}