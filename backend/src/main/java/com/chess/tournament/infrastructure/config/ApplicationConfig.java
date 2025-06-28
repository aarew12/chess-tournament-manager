package com.chess.tournament.infrastructure.config;

import com.chess.tournament.application.usecase.CreateTournamentUseCase;
import com.chess.tournament.application.usecase.RegisterPlayerUseCase;
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
}
