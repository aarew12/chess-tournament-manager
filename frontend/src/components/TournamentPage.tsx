import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { apiService, TournamentDetails, TournamentPlayer, Pairing } from '../services/api';
import { RegisterPlayerForm } from './RegisterPlayerForm';

export const TournamentPage: React.FC = () => {
  const { tournamentId } = useParams<{ tournamentId: string }>();
  const navigate = useNavigate();

  const [tournament, setTournament] = useState<TournamentDetails | null>(null);
  const [players, setPlayers] = useState<TournamentPlayer[]>([]);
  const [pairings, setPairings] = useState<Pairing[]>([]);
  const [activeTab, setActiveTab] = useState<'register' | 'view'>('register');
  const [error, setError] = useState<string | null>(null);

  const loadTournamentData = async () => {
    if (!tournamentId) return;

    setError(null);

    try {
      const [tournamentDetails, playerList] = await Promise.all([
        apiService.getTournamentDetails(tournamentId),
        apiService.getTournamentPlayers(tournamentId)
      ]);

      setTournament(tournamentDetails);
      setPlayers(playerList);

      if (tournamentDetails.status === 'IN_PROGRESS') {
        const pairingList = await apiService.getPairings(tournamentId, 1);
        setPairings(pairingList);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load tournament data');
    }
  };

  const startTournament = async () => {
    if (!tournamentId) return;

    try {
      await apiService.startTournament(tournamentId);
      await loadTournamentData();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to start tournament');
    }
  };

  const refreshPlayers = () => {
    loadTournamentData();
  };

  useEffect(() => {
    loadTournamentData();
  }, [tournamentId]);

  if (!tournamentId) {
    navigate('/');
    return null;
  }

  if (error) {
    return (
      <div className="card" style={{ maxWidth: '600px', width: '100%', textAlign: 'center' }}>
        <h2>Error Loading Tournament</h2>
        <div style={{
          color: 'red',
          backgroundColor: '#ffe6e6',
          padding: '20px',
          borderRadius: '8px',
          marginBottom: '20px'
        }}>
          {error}
        </div>
        <button 
          onClick={() => navigate('/')}
          className="btn-primary"
        >
          Back to Home
        </button>
      </div>
    );
  }

  if (!tournament) {
    return (
      <div className="card" style={{ maxWidth: '600px', width: '100%', textAlign: 'center' }}>
        <h2>Tournament Not Found</h2>
        <p>The tournament you're looking for doesn't exist.</p>
        <button 
          onClick={() => navigate('/')}
          className="btn-primary"
        >
          Back to Home
        </button>
      </div>
    );
  }

  const canStartTournament = tournament.status === 'PLANNED' && players.length >= 2;
  const tournamentStarted = tournament.status === 'IN_PROGRESS';

  return (
    <div className="card" style={{ maxWidth: '900px', width: '100%' }}>
      <div style={{ textAlign: 'center', marginBottom: '30px', position: 'relative' }}>
        <button
          onClick={() => navigate('/')}
          className="btn-secondary"
          style={{
            position: 'absolute',
            top: '0',
            right: '0'
          }}
        >
          New Tournament
        </button>
        <h1 style={{ fontSize: '24px', margin: '10px 0', paddingRight: '120px' }}>
          {tournament.name}
        </h1>
        <p style={{ fontSize: '16px', color: '#666', margin: '0 0 10px 0' }}>
          {tournament.description}
        </p>
        <span style={{
          padding: '4px 12px',
          backgroundColor: tournament.status === 'PLANNED' ? '#27ae60' : '#3498db',
          color: 'white',
          borderRadius: '4px',
          fontSize: '14px'
        }}>
          {tournament.type === 'ROUND_ROBIN' ? 'Round Robin' : 'Single Elimination'} • {tournament.status}
        </span>
      </div>

      {error && (
        <div style={{
          color: 'red',
          backgroundColor: '#ffe6e6',
          padding: '10px',
          borderRadius: '4px',
          marginBottom: '20px'
        }}>
          {error}
        </div>
      )}

      <nav style={{ marginBottom: '20px', borderBottom: '1px solid #ddd' }}>
        <button
          onClick={() => setActiveTab('register')}
          style={{
            margin: '0 10px 0 0',
            padding: '10px 20px',
            backgroundColor: 'transparent',
            color: activeTab === 'register' ? '#3498db' : '#666',
            border: 'none',
            borderBottom: activeTab === 'register' ? '2px solid #3498db' : '2px solid transparent',
            cursor: 'pointer',
            fontSize: '16px'
          }}
        >
          Register Players
        </button>
        <button
          onClick={() => setActiveTab('view')}
          style={{
            margin: '0 10px',
            padding: '10px 20px',
            backgroundColor: 'transparent',
            color: activeTab === 'view' ? '#3498db' : '#666',
            border: 'none',
            borderBottom: activeTab === 'view' ? '2px solid #3498db' : '2px solid transparent',
            cursor: 'pointer',
            fontSize: '16px'
          }}
        >
          Tournament View
        </button>
      </nav>

      {activeTab === 'register' ? (
        <RegisterPlayerForm
          tournamentId={tournamentId}
          onPlayerRegistered={refreshPlayers}
          disabled={tournament?.status === 'IN_PROGRESS'}
        />
      ) : (
        <div>
          <div style={{ marginBottom: '30px' }}>
            <h2 style={{ marginBottom: '15px' }}>
              Registered Players ({players.length})
            </h2>

            {players.length === 0 ? (
              <p style={{ color: '#666', fontStyle: 'italic' }}>No players registered yet.</p>
            ) : (
              <table style={{
                width: '100%',
                borderCollapse: 'collapse',
                backgroundColor: 'white',
                boxShadow: '0 2px 4px rgba(0,0,0,0.1)',
                borderRadius: '8px',
                overflow: 'hidden'
              }}>
                <thead>
                  <tr style={{ backgroundColor: '#f8f9fa' }}>
                    <th style={{
                      padding: '12px',
                      textAlign: 'left',
                      fontWeight: 'bold',
                      color: '#333',
                      borderBottom: '2px solid #dee2e6'
                    }}>
                      #
                    </th>
                    <th style={{
                      padding: '12px',
                      textAlign: 'left',
                      fontWeight: 'bold',
                      color: '#333',
                      borderBottom: '2px solid #dee2e6'
                    }}>
                      Player Name
                    </th>
                    <th style={{
                      padding: '12px',
                      textAlign: 'center',
                      fontWeight: 'bold',
                      color: '#333',
                      borderBottom: '2px solid #dee2e6'
                    }}>
                      Rating
                    </th>
                  </tr>
                </thead>
                <tbody>
                  {players.map((player, index) => (
                    <tr key={player.id} style={{
                      backgroundColor: index % 2 === 0 ? '#ffffff' : '#f8f9fa',
                      transition: 'background-color 0.2s'
                    }}>
                      <td style={{
                        padding: '12px',
                        borderBottom: '1px solid #dee2e6',
                        fontWeight: 'bold',
                        color: '#007bff'
                      }}>
                        {index + 1}
                      </td>
                      <td style={{
                        padding: '12px',
                        borderBottom: '1px solid #dee2e6',
                        color: '#333'
                      }}>
                        {player.name}
                      </td>
                      <td style={{
                        padding: '12px',
                        textAlign: 'center',
                        borderBottom: '1px solid #dee2e6',
                        color: '#333',
                        fontWeight: 'bold'
                      }}>
                        {player.rating}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>

          {canStartTournament && (
            <div style={{ textAlign: 'center', marginBottom: '20px' }}>
              <button
                onClick={startTournament}
                className="btn-primary"
                style={{ padding: '12px 24px' }}
              >
                {'Start Tournament'}
              </button>
            </div>
          )}

          {tournamentStarted && pairings.length > 0 && (
            <div>
              <h2 style={{ marginBottom: '15px' }}>
                {tournament.type === 'ROUND_ROBIN' ? 'Round 1 Pairings' : 'First Round Bracket'}
              </h2>

              <div style={{ display: 'grid', gap: '15px' }}>
                {pairings.map((pairing, index) => (
                  <div key={index} style={{
                    padding: '20px',
                    border: '2px solid #007bff',
                    borderRadius: '8px',
                    backgroundColor: '#f8f9ff',
                    display: 'flex',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    boxShadow: '0 2px 4px rgba(0,0,0,0.1)'
                  }}>
                    <div style={{ flex: 1, textAlign: 'center' }}>
                      <div style={{ fontSize: '1.1em', fontWeight: 'bold', color: '#333' }}>
                        {pairing.whitePlayerName}
                      </div>
                      <div style={{ fontSize: '0.9em', color: '#666' }}>White</div>
                    </div>

                    <div style={{
                      margin: '0 20px',
                      fontSize: '1.5em',
                      fontWeight: 'bold',
                      color: '#007bff'
                    }}>
                      VS
                    </div>

                    <div style={{ flex: 1, textAlign: 'center' }}>
                      <div style={{ fontSize: '1.1em', fontWeight: 'bold', color: '#333' }}>
                        {pairing.blackPlayerName}
                      </div>
                      <div style={{ fontSize: '0.9em', color: '#666' }}>Black</div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};