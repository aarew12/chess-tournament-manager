package com.chess.tournament.infrastructure.adapter.web;

import com.chess.tournament.application.exception.PlayerAlreadyRegisteredException;
import com.chess.tournament.application.exception.TournamentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TournamentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTournamentNotFound(TournamentNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "TOURNAMENT_NOT_FOUND",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(PlayerAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handlePlayerAlreadyRegistered(PlayerAlreadyRegisteredException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "PLAYER_ALREADY_REGISTERED",
                ex.getMessage(),
                HttpStatus.CONFLICT.value()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "BAD_REQUEST",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}