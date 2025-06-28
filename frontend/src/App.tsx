import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import { CreateTournamentForm } from './components/CreateTournamentForm';
import { TournamentPage } from './components/TournamentPage';

function App() {
  return (
    <Router>
      <div className="App">
        <header className="App-header">
          <h1>Chess Tournament Manager</h1>
        </header>
        
        <main className="main-content">
          <Routes>
            <Route path="/" element={<CreateTournamentForm />} />
            <Route path="/tournament/:tournamentId" element={<TournamentPage />} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;