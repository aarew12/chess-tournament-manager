import React, { useState } from 'react';
import { apiService, RegisterPlayerRequest } from '../services/api';
import { useAlert } from '../hooks/useAlert';
import { Alert } from './Alert';

interface Props {
  tournamentId: string;
  onPlayerRegistered?: () => void;
  disabled?: boolean;
}

export const RegisterPlayerForm: React.FC<Props> = ({ tournamentId, onPlayerRegistered, disabled = false }) => {
  const [formData, setFormData] = useState<RegisterPlayerRequest>({
    playerName: '',
    rating: 1000,
  });
  const [ratingInput, setRatingInput] = useState<string>('1000');

  const [loading, setLoading] = useState(false);
  const { alerts, hideAlert, showError, showSuccess } = useAlert();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const sanitizedData = {
        playerName: formData.playerName.trim(),
        rating: Math.max(1, formData.rating),
      };

      await apiService.registerPlayer(tournamentId, sanitizedData);
      showSuccess('Player registered successfully!');

      setFormData({ playerName: '', rating: 1000 });
      setRatingInput('1000');

      setTimeout(() => {
        onPlayerRegistered?.();
      }, 1000);
    } catch (err) {
      showError(err instanceof Error ? err.message : 'Failed to register player');
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {alerts.map(alert => (
        <Alert
          key={alert.id}
          type={alert.type}
          message={alert.message}
          onClose={() => hideAlert(alert.id)}
        />
      ))}

      <div style={{ maxWidth: '500px', margin: '0 auto' }}>
        <h2 style={{ marginBottom: '20px', textAlign: 'center' }}>Register New Player</h2>
        
        {disabled && (
          <div style={{
            color: '#6c757d',
            backgroundColor: '#f8f9fa',
            border: '1px solid #dee2e6',
            padding: '10px',
            borderRadius: '4px',
            marginBottom: '15px',
            textAlign: 'center'
          }}>
            Player registration is disabled after tournament starts
          </div>
        )}

      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="form-label">
            Player Name:
            <input
              type="text"
              value={formData.playerName}
              onChange={(e) => setFormData({ ...formData, playerName: e.target.value })}
              required
              minLength={2}
              maxLength={50}
              className="form-input"
              disabled={disabled}
            />
          </label>
        </div>

        <div className="form-group">
          <label className="form-label">
            Rating:
            <input
              type="text"
              value={ratingInput}
              onChange={(e) => {
                const value = e.target.value;
                
                if (value === '' || /^\d+$/.test(value)) {
                  setRatingInput(value);
                  
                  const numValue = parseInt(value);
                  if (!isNaN(numValue) && numValue > 0) {
                    setFormData({ ...formData, rating: numValue });
                  } else if (value === '') {
                    setFormData({ ...formData, rating: 0 });
                  }
                }
              }}
              onBlur={() => {
                if (ratingInput === '' || parseInt(ratingInput) <= 0) {
                  setRatingInput('1000');
                  setFormData({ ...formData, rating: 1000 });
                }
              }}
              className="form-input"
              disabled={disabled}
            />
          </label>
        </div>

        <button
          type="submit"
          disabled={loading || disabled}
          className="btn-primary"
          style={{
            width: '100%',
            padding: '12px'
          }}
        >
          {loading ? 'Registering...' : 'Register Player'}
        </button>
      </form>
      </div>
    </>
  );
};