# Chess Tournament Manager

A full-stack application demonstrating modern software development practices with Java backend and React frontend. This project showcases microservices architecture, RESTful APIs, Test-Driven Development (TDD), containerization, and responsive design patterns.

## Tech Stack

**Backend:**
- Java 21 with Spring Boot
- RESTful API design
- Hexagonal Architecture (Clean Architecture)
- Domain-Driven Design principles
- JUnit 5 for unit testing
- Docker containerization

**Frontend:**
- React 18 with TypeScript
- Responsive design
- Custom notification system
- Async API integration with proper error handling
- Input validation and sanitization

## Features

- Create and manage chess tournaments (Round Robin & Single Elimination)
- Player registration with rating system
- Tournament bracket generation and pairing logic
- Real-time UI updates without page reloads
- Comprehensive error handling with user-friendly notifications
- Input sanitization and validation
- Responsive design for various screen sizes

## Quick Start

### Prerequisites
- Docker and Docker Compose
- Node.js 18+ (for local frontend development)
- Java 21 (for local backend development)

### Running with Docker
```bash
docker-compose up --build
```

### Running Locally

**Backend:**
```bash
cd backend
./gradlew bootRun
```

**Frontend:**
```bash
cd frontend
npm install
npm start
```

## Architecture

The project follows clean architecture principles with hexagonal architecture pattern:

- **Domain Layer:** Core business logic and entities
- **Application Layer:** Use cases and application services
- **Infrastructure Layer:** External adapters (REST controllers, repositories)

## API Endpoints

- `POST /api/tournaments` - Create tournament
- `GET /api/tournaments/{id}` - Get tournament details
- `POST /api/tournaments/{id}/players` - Register player
- `GET /api/tournaments/{id}/players` - Get tournament players
- `POST /api/tournaments/{id}/start` - Start tournament
- `GET /api/tournaments/{id}/pairings/{round}` - Get round pairings
