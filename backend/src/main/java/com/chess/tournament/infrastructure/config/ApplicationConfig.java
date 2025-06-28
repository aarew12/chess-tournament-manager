package com.chess.tournament.infrastructure.config;

import com.chess.tournament.application.usecase.*;
import com.chess.tournament.domain.port.TournamentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public RegisterPlayerUseCase registerPlayerUseCase(TournamentRepository repository) {
        return new RegisterPlayerUseCase(repository);
    }

    @Bean
    public CreateTournamentUseCase createTournamentUseCase(TournamentRepository repository) {
        return new CreateTournamentUseCase(repository);
    }
    
    @Bean
    public StartTournamentUseCase startTournamentUseCase(TournamentRepository repository) {
        return new StartTournamentUseCase(repository);
    }

    @Bean
    public GetTournamentPlayersUseCase getTournamentPlayersUseCase(TournamentRepository repository) {
        return new GetTournamentPlayersUseCase(repository);
    }

    @Bean
    public GeneratePairingsUseCase generatePairingsUseCase(TournamentRepository repository) {
        return new GeneratePairingsUseCase(repository);
    }

    @Bean
    public GetTournamentUseCase getTournament(TournamentRepository repository) {
        return new GetTournamentUseCase(repository);
    }
}
