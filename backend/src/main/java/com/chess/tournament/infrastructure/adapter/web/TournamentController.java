package com.chess.tournament.infrastructure.adapter.web;

import com.chess.tournament.application.usecase.CreateTournamentCommand;
import com.chess.tournament.application.usecase.CreateTournamentUseCase;
import com.chess.tournament.application.usecase.RegisterPlayerCommand;
import com.chess.tournament.application.usecase.RegisterPlayerUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final RegisterPlayerUseCase registerPlayerUseCase;
    private final CreateTournamentUseCase createTournamentUseCase;

    public TournamentController(RegisterPlayerUseCase registerPlayerUseCase, CreateTournamentUseCase createTournamentUseCase) {
        this.registerPlayerUseCase = registerPlayerUseCase;
        this.createTournamentUseCase = createTournamentUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createTournament(@RequestBody CreateTournamentRequest request) {
        var command = new CreateTournamentCommand(
                request.name(),
                request.description(),
                request.startDateTime()
        );

        String tournamentId = createTournamentUseCase.execute(command);
        return ResponseEntity.ok(tournamentId);
    }

    @PostMapping("/{tournamentId}/players")
    public ResponseEntity<Void> registerPlayer(@PathVariable String tournamentId, @RequestBody RegisterPlayerRequest request) {

        var command = new RegisterPlayerCommand(tournamentId, request.playerName(), request.rating());

        registerPlayerUseCase.execute(command);

        return ResponseEntity.ok().build();
    }
}
