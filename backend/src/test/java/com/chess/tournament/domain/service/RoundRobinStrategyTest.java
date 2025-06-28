package com.chess.tournament.domain.service;

import com.chess.tournament.domain.model.PlayerId;
import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Round Robin Strategy")
class RoundRobinStrategyTest {

    private final RoundRobinStrategy strategy = new RoundRobinStrategy();

    @Test
    @DisplayName("Should generate pairings for even number of players")
    void shouldGeneratePairingsForEvenNumberOfPlayers() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.ROUND_ROBIN);

        tournament.registerPlayer(PlayerId.generate(), "Player 1", 1200);
        tournament.registerPlayer(PlayerId.generate(), "Player 2", 1300);
        tournament.registerPlayer(PlayerId.generate(), "Player 3", 1400);
        tournament.registerPlayer(PlayerId.generate(), "Player 4", 1500);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(2)
                            .allMatch(p -> p.whitePlayer() != null && p.blackPlayer() != null);
    }

    @Test
    @DisplayName("Should generate pairings for odd number of players with bye")
    void shouldGeneratePairingsForOddNumberOfPlayersWithBye() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.ROUND_ROBIN);

        tournament.registerPlayer(PlayerId.generate(), "Player 1", 1200);
        tournament.registerPlayer(PlayerId.generate(), "Player 2", 1300);
        tournament.registerPlayer(PlayerId.generate(), "Player 3", 1400);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(2);
        assertThat(pairings.get(0).whitePlayer()).isNotNull();
        assertThat(pairings.get(0).blackPlayer()).isNull();
        assertThat(pairings.get(1).whitePlayer()).isNotNull();
        assertThat(pairings.get(1).blackPlayer()).isNotNull();
    }

    @Test
    @DisplayName("Should generate empty pairings for single player")
    void shouldGenerateEmptyPairingsForSinglePlayer() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.ROUND_ROBIN);

        tournament.registerPlayer(PlayerId.generate(), "Player 1", 1200);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).isEmpty();
    }

    @Test
    @DisplayName("Should generate empty pairings for no players")
    void shouldGenerateEmptyPairingsForNoPlayers() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.ROUND_ROBIN);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).isEmpty();
    }

    @Test
    @DisplayName("Should use correct player names in pairings")
    void shouldUseCorrectPlayerNamesInPairings() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.ROUND_ROBIN);

        tournament.registerPlayer(PlayerId.generate(), "Magnus", 2800);
        tournament.registerPlayer(PlayerId.generate(), "Hikaru", 2750);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(1);
        var pairing = pairings.getFirst();
        assertThat(pairing.whitePlayerName()).isEqualTo("Magnus");
        assertThat(pairing.blackPlayerName()).isEqualTo("Hikaru");
    }
}