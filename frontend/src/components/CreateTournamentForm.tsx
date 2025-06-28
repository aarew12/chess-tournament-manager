import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiService, CreateTournamentRequest } from '../services/api';
import { useAlert } from '../hooks/useAlert';
import { Alert } from './Alert';

export const CreateTournamentForm: React.FC = () => {
  const navigate = useNavigate();
  const { alerts, hideAlert, showError, showSuccess } = useAlert();

  const [formData, setFormData] = useState<CreateTournamentRequest>({
    name: '',
    description: '',
    startDate: '',
    type: 'ROUND_ROBIN',
  });

  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);

    try {
      const sanitizedData = {
        ...formData,
        name: formData.name.trim(),
        description: formData.description.trim(),
        startDate: formData.startDate,
      };

      const tournamentId = await apiService.createTournament(sanitizedData);
      showSuccess('Tournament created successfully!');

      setTimeout(() => {
        navigate(`/tournament/${tournamentId}`);
      }, 1000);
    } catch (err) {
      showError(err instanceof Error ? err.message : 'Failed to create tournament');
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

      <div className="card" style={{ maxWidth: '500px', width: '100%' }}>
        <h2 style={{ marginBottom: '20px', textAlign: 'center' }}>Create New Tournament</h2>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">
              Tournament Name:
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
                minLength={3}
                maxLength={100}
                className="form-input"
              />
            </label>
          </div>

          <div className="form-group">
            <label className="form-label">
              Description:
              <textarea
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                maxLength={500}
                className="form-input"
                style={{ height: '80px', resize: 'vertical' }}
              />
            </label>
          </div>

          <div className="form-group">
            <label className="form-label">
              Start Date:
              <input
                type="date"
                value={formData.startDate}
                onChange={(e) => setFormData({ ...formData, startDate: e.target.value })}
                required
                min={new Date().toISOString().split('T')[0]}
                className="form-input"
              />
            </label>
          </div>

          <div className="form-group">
            <label className="form-label">
              Tournament Type:
              <select
                value={formData.type}
                onChange={(e) => setFormData({ ...formData, type: e.target.value as 'ROUND_ROBIN' | 'SINGLE_ELIMINATION' })}
                className="form-input"
              >
                <option value="ROUND_ROBIN">Round Robin</option>
                <option value="SINGLE_ELIMINATION">Single Elimination</option>
              </select>
            </label>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="btn-primary"
            style={{ width: '100%', padding: '12px' }}
          >
            {loading ? 'Creating...' : 'Create Tournament'}
          </button>
        </form>
      </div>
    </>
  );
};