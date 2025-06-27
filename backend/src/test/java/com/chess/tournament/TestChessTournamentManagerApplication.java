package com.chess.tournament;

import org.springframework.boot.SpringApplication;

public class TestChessTournamentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.from(ChessTournamentManagerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
