package com.chess.tournament.application.exception;

public class TournamentNotFoundException extends RuntimeException {
    
    public TournamentNotFoundException(String tournamentId) {
        super("Tournament with id '" + tournamentId + "' not found");
    }
}