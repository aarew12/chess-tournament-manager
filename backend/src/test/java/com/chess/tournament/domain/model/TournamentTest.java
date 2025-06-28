package com.chess.tournament.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tournament Domain Entity")
class TournamentTest {

    @Test
    @DisplayName("Should create tournament with valid data")
    void shouldCreateTournamentWithValidData() {
        // Given
        String name = "Spring Chess Championship";
        String description = "Annual chess tournament";
        LocalDate startDate = LocalDate.now().plusDays(7);

        // When
        Tournament tournament = Tournament.create(name, description, startDate, TournamentType.ROUND_ROBIN);

        // Then
        assertThat(tournament).isNotNull();
        assertThat(tournament.getName()).isEqualTo(name);
        assertThat(tournament.getDescription()).isEqualTo(description);
        assertThat(tournament.getStartDate()).isEqualTo(startDate);
        assertThat(tournament.getStatus()).isEqualTo(TournamentStatus.PLANNED);
    }

    @Test
    @DisplayName("Should not create when name is null or empty")
    void shouldNotCreateWhenNameIsNullOrEmpty() {
        LocalDate futureDate = LocalDate.now().plusDays(7);

        assertThatThrownBy(() -> Tournament.create(null, "Description", futureDate, TournamentType.ROUND_ROBIN)).isInstanceOf(IllegalArgumentException.class).hasMessage("Tournament name cannot be empty");

        assertThatThrownBy(() -> Tournament.create("", "Description", futureDate, TournamentType.ROUND_ROBIN)).isInstanceOf(IllegalArgumentException.class).hasMessage("Tournament name cannot be empty");
    }

    @Test
    @DisplayName("Should not create when start date is in the past")
    void shouldNotCreateWhenStartDateIsInThePast() {
        LocalDate pastDate = LocalDate.now().minusDays(1);

        assertThatThrownBy(() -> Tournament.create("Valid Name", "Description", pastDate, TournamentType.ROUND_ROBIN)).isInstanceOf(IllegalArgumentException.class).hasMessage("Tournament start date must be in the future");
    }

    @Test
    @DisplayName("Should not create when type is null")
    void shouldNotCreateWhenTypeIsNull() {
        LocalDate futureDate = LocalDate.now().plusDays(7);

        assertThatThrownBy(() -> Tournament.create("Valid Name", "Description", futureDate, null)).isInstanceOf(IllegalArgumentException.class).hasMessage("Tournament type cannot be empty");
    }

    @Test
    @DisplayName("Should register player successfully")
    void shouldRegisterPlayerSuccessfully() {
        // Given
        LocalDate futureDate = LocalDate.now().plusDays(7);
        Tournament tournament = Tournament.create("Test Tournament", "Description", futureDate, TournamentType.ROUND_ROBIN);
        PlayerId playerId = PlayerId.generate();
        String playerName = "Magnus Carlsen";
        int rating = 2800;

        // When
        tournament.registerPlayer(playerId, playerName, rating);

        // Then
        assertThat(tournament.getRegisteredPlayers()).hasSize(1);
        assertThat(tournament.isPlayerRegistered(playerId)).isTrue();
    }

    @Test
    @DisplayName("Should not to register player with same id twice")
    void shouldNotToRegisterPlayerWithSameIdTwice() {
        // Given
        LocalDate futureDate = LocalDate.now().plusDays(7);
        Tournament tournament = Tournament.create("Test Tournament", "Description", futureDate, TournamentType.ROUND_ROBIN);
        PlayerId playerId = PlayerId.generate();
        String playerName = "Magnus Carlsen";
        int rating = 2800;

        tournament.registerPlayer(playerId, playerName, rating);

        // When // Then
        assertThatThrownBy(() -> tournament.registerPlayer(playerId, playerName, rating)).isInstanceOf(IllegalStateException.class).hasMessage("Player is already registered for this tournament");
    }

    @Test
    @DisplayName("Should not to register player with same name and ranking twice")
    void shouldNotToRegisterPlayerWithSameNameAndRankingTwice() {
        // Given
        LocalDate futureDate = LocalDate.now().plusDays(7);
        Tournament tournament = Tournament.create("Test Tournament", "Description", futureDate, TournamentType.ROUND_ROBIN);
        PlayerId playerId = PlayerId.generate();
        PlayerId otherPlayerId = PlayerId.generate();
        String playerName = "Magnus Carlsen";
        int rating = 2800;

        tournament.registerPlayer(playerId, playerName, rating);

        // When // Then
        assertThatThrownBy(() -> tournament.registerPlayer(otherPlayerId, playerName, rating)).isInstanceOf(IllegalStateException.class).hasMessage("Player is already registered for this tournament");
    }

    @Test
    @DisplayName("Should register player with same name but different ranking")
    void shouldRegisterPlayerWithSameNameButDifferentRanking() {
        // Given
        LocalDate futureDate = LocalDate.now().plusDays(7);
        Tournament tournament = Tournament.create("Test Tournament", "Description", futureDate, TournamentType.ROUND_ROBIN);
        PlayerId playerId = PlayerId.generate();
        PlayerId otherPlayerId = PlayerId.generate();
        String playerName = "Magnus Carlsen";
        int rating = 2800;
        int otherRating = 2800;

        tournament.registerPlayer(playerId, playerName, rating);

        // When // Then
        assertThatThrownBy(() -> tournament.registerPlayer(otherPlayerId, playerName, otherRating)).isInstanceOf(IllegalStateException.class).hasMessage("Player is already registered for this tournament");
    }
}