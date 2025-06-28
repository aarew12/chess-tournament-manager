const API_BASE_URL = 'http://localhost:8080/api';

export interface TournamentDetails {
  id: string;
  name: string;
  description: string;
  type: 'ROUND_ROBIN' | 'SINGLE_ELIMINATION';
  status: 'PLANNED' | 'IN_PROGRESS' | 'COMPLETED' | 'CANCELLED';
}

export interface CreateTournamentRequest {
  name: string;
  description: string;
  startDate: string;
  type: 'ROUND_ROBIN' | 'SINGLE_ELIMINATION';
}

export interface TournamentPlayer {
  id: string;
  name: string;
  rating: number;
}

export interface Pairing {
  whitePlayer: string;
  blackPlayer: string;
  whitePlayerName: string;
  blackPlayerName: string;
}

export interface RegisterPlayerRequest {
  playerName: string;
  rating: number;
}

class ApiService {
private async handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
      let errorMessage = 'An unexpected error occurred';

      try {
        const contentType = response.headers.get('content-type');
        if (contentType && contentType.includes('application/json')) {
          const errorData = await response.json();
          if (errorData.message) {
            errorMessage = errorData.message;
          } else if (errorData.error) {
            errorMessage = errorData.error;
          }
        } else {
          const textData = await response.text();
          if (textData) {
            errorMessage = textData;
          }
        }
      } catch {
        switch (response.status) {
          case 400:
            errorMessage = 'Invalid request data';
            break;
          case 404:
            errorMessage = 'Resource not found';
            break;
          case 409:
            errorMessage = 'Conflict - this action cannot be performed';
            break;
          case 500:
            errorMessage = 'Server error - please try again later';
            break;
          default:
            errorMessage = `Error ${response.status}: ${response.statusText}`;
        }
      }

      throw new Error(errorMessage);
    }

    const contentType = response.headers.get('content-type');
    if (contentType && contentType.includes('application/json')) {
      return response.json();
    } else {
      return response.text() as any;
    }
  }

  async createTournament(tournament: CreateTournamentRequest): Promise<string> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(tournament),
      });

      return this.handleResponse<string>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }

  async registerPlayer(tournamentId: string, player: RegisterPlayerRequest): Promise<void> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments/${tournamentId}/players`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(player),
      });

      await this.handleResponse<void>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }

  async startTournament(tournamentId: string): Promise<void> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments/${tournamentId}/start`, {
        method: 'POST',
      });

      await this.handleResponse<void>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }

  async getTournamentDetails(tournamentId: string): Promise<TournamentDetails> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments/${tournamentId}`);
      return this.handleResponse<TournamentDetails>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }

  async getTournamentPlayers(tournamentId: string): Promise<TournamentPlayer[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments/${tournamentId}/players`);
      return this.handleResponse<TournamentPlayer[]>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }

  async getPairings(tournamentId: string, round: number = 1): Promise<Pairing[]> {
    try {
      const response = await fetch(`${API_BASE_URL}/tournaments/${tournamentId}/pairings?round=${round}`);
      return this.handleResponse<Pairing[]>(response);
    } catch (error) {
      if (error instanceof TypeError && error.message.includes('fetch')) {
        throw new Error('Cannot connect to server. Please check if the backend is running.');
      }
      throw error;
    }
  }
}

export const apiService = new ApiService();