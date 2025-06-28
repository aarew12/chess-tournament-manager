package com.chess.tournament.domain.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Tournament {

    private final TournamentId id;
    private final String name;
    private final String description;
    private final LocalDateTime startDateTime;
    private final TournamentStatus status;
    private final Map<PlayerId, TournamentPlayer> registeredPlayers;

    private Tournament(String name, String description, LocalDateTime startDateTime) {
        this.id = TournamentId.generate();
        this.name = name;
        this.description = description;
        this.startDateTime = startDateTime;
        this.status = TournamentStatus.PLANNED;
        this.registeredPlayers = new HashMap<>();
    }

    public static Tournament create(String name, String description, LocalDateTime startDateTime) {
        validateTournamentData(name, startDateTime);
        return new Tournament(name, description, startDateTime);
    }

    private static void validateTournamentData(String name, LocalDateTime startDateTime) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tournament name cannot be null or empty");
        }

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Tournament start date must be in the future");
        }
    }

    public void registerPlayer(PlayerId playerId, String playerName, int rating) {
        validatePlayerRegistration(playerId, playerName, rating);

        var player = new TournamentPlayer(playerId, playerName, rating);
        registeredPlayers.put(playerId, player);
    }

    private void validatePlayerRegistration(PlayerId playerId, String playerName, int rating) {
        if (registeredPlayers.containsKey(playerId)) {
            throw new IllegalStateException("Player is already registered for this tournament");
        }

        boolean playerExists = registeredPlayers.values().stream().anyMatch(player -> player.name().equals(playerName) && player.rating() == rating);

        if (playerExists) {
            throw new IllegalStateException("Player is already registered for this tournament");
        }
    }

    public Collection<TournamentPlayer> getRegisteredPlayers() {
        return Collections.unmodifiableCollection(registeredPlayers.values());
    }

    public boolean isPlayerRegistered(PlayerId playerId) {
        return registeredPlayers.containsKey(playerId);
    }

    public TournamentId getId() {
        return id;
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
