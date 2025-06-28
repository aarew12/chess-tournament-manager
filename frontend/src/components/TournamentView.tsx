import React, { useState, useEffect } from 'react';
import { apiService, TournamentPlayer, Pairing } from '../services/api';

interface Props {
  tournamentId: string;
  tournamentType: 'ROUND_ROBIN' | 'SINGLE_ELIMINATION';
}

export const TournamentView: React.FC<Props> = ({ tournamentId, tournamentType }) => {
  const [players, setPlayers] = useState<TournamentPlayer[]>([]);
  const [pairings, setPairings] = useState<Pairing[]>([]);
  const [tournamentStarted, setTournamentStarted] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadPlayers = async () => {
    try {
      const playerList = await apiService.getTournamentPlayers(tournamentId);
      setPlayers(playerList);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load players');
    }
  };

  const startTournament = async () => {
    setLoading(true);
    try {
      await apiService.startTournament(tournamentId);
      setTournamentStarted(true);

      const pairingList = await apiService.getPairings(tournamentId, 1);
      setPairings(pairingList);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to start tournament');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadPlayers();
  }, [tournamentId]);

  return (
    <div style={{ maxWidth: '600px', margin: '0 auto', padding: '20px' }}>
      <h2>Tournament: {tournamentId}</h2>
      <p>Type: {tournamentType === 'ROUND_ROBIN' ? 'Round Robin' : 'Single Elimination'}</p>

      {error && (
        <div style={{ color: 'red', marginBottom: '10px' }}>
          {error}
        </div>
      )}

      <div style={{ marginBottom: '20px' }}>
        <h3>Registered Players ({players.length})</h3>
        {players.length === 0 ? (
          <p>No players registered yet.</p>
        ) : (
          <ul style={{ listStyle: 'none', padding: 0 }}>
            {players.map((player, index) => (
              <li key={player.id} style={{
                padding: '8px',
                margin: '5px 0',
                backgroundColor: '#f5f5f5',
                borderRadius: '4px'
              }}>
                {index + 1}. {player.name} (Rating: {player.rating})
              </li>
            ))}
          </ul>
        )}
      </div>

      {!tournamentStarted && players.length >= 2 && (
        <button
          onClick={startTournament}
          disabled={loading}
          style={{
            padding: '12px 20px',
            backgroundColor: loading ? '#ccc' : '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '4px',
            cursor: loading ? 'not-allowed' : 'pointer',
            marginBottom: '20px'
          }}
        >
          {loading ? 'Starting...' : 'Start Tournament'}
        </button>
      )}

      {tournamentStarted && pairings.length > 0 && (
        <div>
          <h3>{tournamentType === 'ROUND_ROBIN' ? 'Round 1 Pairings' : 'First Round Bracket'}</h3>
          <div style={{ display: 'grid', gap: '10px' }}>
            {pairings.map((pairing, index) => (
              <div key={index} style={{
                padding: '10px',
                border: '1px solid #ddd',
                borderRadius: '4px',
                backgroundColor: '#f9f9f9'
              }}>
                <strong>{pairing.whitePlayerName}</strong> (White)
                {pairing.blackPlayerName ? (
                  <>
                    <span style={{ margin: '0 10px' }}>vs</span>
                    <strong>{pairing.blackPlayerName}</strong> (Black)
                  </>
                ) : (
                  <div style={{ marginTop: '5px' }}>
                    <strong>BYE</strong>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
};