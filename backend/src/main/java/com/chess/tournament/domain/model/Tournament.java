package com.chess.tournament.domain.model;

import com.chess.tournament.application.exception.PlayerAlreadyRegisteredException;

import java.time.LocalDate;
import java.util.*;

public class Tournament {

    private final TournamentId id;
    private final String name;
    private final String description;
    private final LocalDate startDate;
    private final TournamentType type;
    private final SequencedMap<PlayerId, TournamentPlayer> registeredPlayers;
    private TournamentStatus status;
    private int currentRound;

    private Tournament(String name, String description, LocalDate startDate, TournamentType type) {
        this.id = TournamentId.generate();
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.status = TournamentStatus.PLANNED;
        this.type = type;
        this.registeredPlayers = new LinkedHashMap<>();
        this.currentRound = 0;
    }

    public static Tournament create(String name, String description, LocalDate startDate, TournamentType type) {
        validateTournamentData(name, startDate, type);
        return new Tournament(name, description, startDate, type);
    }

    private static void validateTournamentData(String name, LocalDate startDate, TournamentType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tournament name cannot be empty");
        }

        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Tournament start date must be in the future");
        }

        if (type == null) {
            throw new IllegalArgumentException("Tournament type cannot be empty");
        }
    }

    public void registerPlayer(PlayerId playerId, String playerName, int rating) {
        validatePlayerRegistration(playerId, playerName);

        var player = new TournamentPlayer(playerId, playerName, rating);
        registeredPlayers.put(playerId, player);
    }

    private void validatePlayerRegistration(PlayerId playerId, String playerName) {
        if (registeredPlayers.containsKey(playerId)) {
            throw new PlayerAlreadyRegisteredException(playerName);
        }

        boolean playerExists = registeredPlayers.values().stream().anyMatch(player -> player.name().equals(playerName));

        if (playerExists) {
            throw new PlayerAlreadyRegisteredException(playerName);
        }
    }

    public void start() {
        if (!status.canStart()) {
            throw new IllegalStateException("Tournament cannot be started in current status: " + status);
        }

        if (registeredPlayers.size() < 2) {
            throw new IllegalStateException("Cannot start tournament with less than 2 players");
        }

        this.status = TournamentStatus.IN_PROGRESS;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public TournamentType getType() {
        return type;
    }

    public int getCurrentRound() {
        return currentRound;
    }
}
