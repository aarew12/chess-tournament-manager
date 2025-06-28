package com.chess.tournament.domain.service;

import com.chess.tournament.domain.model.PlayerId;
import com.chess.tournament.domain.model.Tournament;
import com.chess.tournament.domain.model.TournamentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Single Elimination Strategy")
class SingleEliminationStrategyTest {

    private final SingleEliminationStrategy strategy = new SingleEliminationStrategy();

    @Test
    @DisplayName("Should generate pairings for power of 2 players")
    void shouldGeneratePairingsForPowerOfTwoPlayers() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        tournament.registerPlayer(PlayerId.generate(), "Player 1", 1200);
        tournament.registerPlayer(PlayerId.generate(), "Player 2", 1300);
        tournament.registerPlayer(PlayerId.generate(), "Player 3", 1400);
        tournament.registerPlayer(PlayerId.generate(), "Player 4", 1500);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(2).allMatch(p -> p.whitePlayer() != null && p.blackPlayer() != null);
    }

    @Test
    @DisplayName("Should generate pairings with byes for non-power of 2 players")
    void shouldGeneratePairingsWithByesForNonPowerOfTwoPlayers() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        tournament.registerPlayer(PlayerId.generate(), "Player 1", 1200);
        tournament.registerPlayer(PlayerId.generate(), "Player 2", 1300);
        tournament.registerPlayer(PlayerId.generate(), "Player 3", 1400);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(2);
        long numByes = pairings.stream().mapToLong(p -> p.blackPlayer() == null ? 1L : 0L).sum();
        assertThat(numByes).isEqualTo(1);
    }

    @ParameterizedTest(name = "Players: {0} → Expected pairings: {1}")
    @CsvSource({"1, 0", "2, 1", "3, 2", "4, 2", "5, 4", "8, 4", "9, 8", "16, 8"})
    @DisplayName("Should calculate correct bracket size for various player counts")
    void shouldCalculateCorrectBracketSize(int numPlayers, int numPairings) {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        for (int i = 0; i < numPlayers; i++) {
            tournament.registerPlayer(PlayerId.generate(), "Player " + (i + 1), 1200);
        }

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(numPairings);
    }

    @Test
    @DisplayName("Should generate correct number of byes for 5 players")
    void shouldGenerateCorrectNumberOfByesForFivePlayers() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        for (int i = 0; i < 5; i++) {
            tournament.registerPlayer(PlayerId.generate(), "Player " + (i + 1), 1200);
        }

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(4);
        long numByes = pairings.stream().mapToLong(p -> p.blackPlayer() == null ? 1L : 0L).sum();
        assertThat(numByes).isEqualTo(3);
    }

    @Test
    @DisplayName("Should handle single player tournament")
    void shouldHandleSinglePlayerTournament() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

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
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).isEmpty();
    }

    @Test
    @DisplayName("Should pair players correctly in bracket order")
    void shouldPairPlayersCorrectlyInBracketOrder() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        PlayerId player1 = PlayerId.generate();
        PlayerId player2 = PlayerId.generate();
        PlayerId player3 = PlayerId.generate();
        PlayerId player4 = PlayerId.generate();

        tournament.registerPlayer(player1, "Player 1", 1200);
        tournament.registerPlayer(player2, "Player 2", 1300);
        tournament.registerPlayer(player3, "Player 3", 1400);
        tournament.registerPlayer(player4, "Player 4", 1500);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(2);

        assertThat(pairings.get(0).whitePlayer()).isEqualTo(player1);
        assertThat(pairings.get(0).blackPlayer()).isEqualTo(player4);

        assertThat(pairings.get(1).whitePlayer()).isEqualTo(player2);
        assertThat(pairings.get(1).blackPlayer()).isEqualTo(player3);
    }

    @Test
    @DisplayName("Should use correct player names in pairings")
    void shouldUseCorrectPlayerNamesInPairings() {
        // Given
        Tournament tournament = Tournament.create("Test Tournament", "Description", LocalDate.now().plusDays(7), TournamentType.SINGLE_ELIMINATION);

        tournament.registerPlayer(PlayerId.generate(), "Alice", 1200);
        tournament.registerPlayer(PlayerId.generate(), "Bob", 1300);

        // When
        var pairings = strategy.generatePairings(tournament, 1);

        // Then
        assertThat(pairings).hasSize(1);
        var pairing = pairings.getFirst();
        assertThat(pairing.whitePlayerName()).isEqualTo("Alice");
        assertThat(pairing.blackPlayerName()).isEqualTo("Bob");
    }
}