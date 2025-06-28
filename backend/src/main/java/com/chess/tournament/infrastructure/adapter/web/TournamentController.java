package com.chess.tournament.infrastructure.adapter.web;

import com.chess.tournament.application.usecase.*;
import com.chess.tournament.domain.model.Pairing;
import com.chess.tournament.domain.model.TournamentPlayer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final RegisterPlayerUseCase registerPlayerUseCase;
    private final CreateTournamentUseCase createTournamentUseCase;
    private final StartTournamentUseCase startTournamentUseCase;
    private final GetTournamentPlayersUseCase getTournamentPlayersUseCase;
    private final GeneratePairingsUseCase generatePairingsUseCase;

    public TournamentController(RegisterPlayerUseCase registerPlayerUseCase, CreateTournamentUseCase createTournamentUseCase, StartTournamentUseCase startTournamentUseCase, GetTournamentPlayersUseCase getTournamentPlayersUseCase, GeneratePairingsUseCase generatePairingsUseCase) {
        this.registerPlayerUseCase = registerPlayerUseCase;
        this.createTournamentUseCase = createTournamentUseCase;
        this.startTournamentUseCase = startTournamentUseCase;
        this.getTournamentPlayersUseCase = getTournamentPlayersUseCase;
        this.generatePairingsUseCase = generatePairingsUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createTournament(@RequestBody CreateTournamentRequest request) {
        var command = new CreateTournamentCommand(request.name(), request.description(), request.startDate(), request.type());

        String tournamentId = createTournamentUseCase.execute(command);
        return ResponseEntity.ok(tournamentId);
    }

    @PostMapping("/{tournamentId}/players")
    public ResponseEntity<Void> registerPlayer(@PathVariable String tournamentId, @RequestBody RegisterPlayerRequest request) {

        var command = new RegisterPlayerCommand(tournamentId, request.playerName(), request.rating());

        registerPlayerUseCase.execute(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{tournamentId}/start")
    public ResponseEntity<Void> startTournament(@PathVariable String tournamentId) {
        startTournamentUseCase.execute(tournamentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tournamentId}/players")
    public ResponseEntity<Collection<TournamentPlayer>> getTournamentPlayers(@PathVariable String tournamentId) {
        var players = getTournamentPlayersUseCase.execute(tournamentId);
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{tournamentId}/pairings")
    public ResponseEntity<List<Pairing>> getPairings(@PathVariable String tournamentId, @RequestParam(defaultValue = "1") int round) {
        var pairings = generatePairingsUseCase.execute(tournamentId, round);
        return ResponseEntity.ok(pairings);
    }
}
