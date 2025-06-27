package com.chess.tournament.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Tournament Domain Entity")
class TournamentTest {

    @Test
    @DisplayName("Should create tournament with valid data")
    void shouldCreateTournamentWithValidData() {
        // Given
        String name = "Spring Chess Championship";
        String description = "Annual chess tournament";
        LocalDateTime startDateTime = LocalDateTime.now().plusDays(7);

        // When
        Tournament tournament = Tournament.create(name, description, startDateTime);

        // Then
        assertThat(tournament).isNotNull();
        assertThat(tournament.getName()).isEqualTo(name);
        assertThat(tournament.getDescription()).isEqualTo(description);
        assertThat(tournament.getStartDateTime()).isEqualTo(startDateTime);
        assertThat(tournament.getStatus()).isEqualTo(TournamentStatus.PLANNED);
    }

    @Test
    @DisplayName("Should not create when name is null or empty")
    void shouldNotCreateWhenNameIsNullOrEmpty() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(7);

        assertThatThrownBy(() ->
                Tournament.create(null, "Description", futureDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tournament name cannot be null or empty");

        assertThatThrownBy(() ->
                Tournament.create("", "Description", futureDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tournament name cannot be null or empty");
    }

    @Test
    @DisplayName("Should not create when start date is in the past")
    void shouldNotCreateWhenStartDateIsInThePast() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        assertThatThrownBy(() ->
                Tournament.create("Valid Name", "Description", pastDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Tournament start date must be in the future");
    }

    @Test
    @DisplayName("Should register player successfully")
    void shouldRegisterPlayerSuccessfully() {
        // Given
        Tournament tournament = Tournament.create(
                "Test Tournament",
                "Description",
                LocalDateTime.now().plusDays(7)
        );
        PlayerId playerId = PlayerId.generate();
        String playerName = "Magnus Carlsen";
        int rating = 2800;

        // When
        tournament.registerPlayer(playerId, playerName, rating);

        // Then
        assertThat(tournament.getRegisteredPlayers()).hasSize(1);
        assertThat(tournament.isPlayerRegistered(playerId)).isTrue();
    }
}