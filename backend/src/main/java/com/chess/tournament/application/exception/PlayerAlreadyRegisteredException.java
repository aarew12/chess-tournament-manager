package com.chess.tournament.application.exception;

public class PlayerAlreadyRegisteredException extends RuntimeException {

    public PlayerAlreadyRegisteredException(String playerName) {
        super("Player with name '" + playerName + "' is already registered for this tournament");
    }
}