import React, { useEffect, useState } from 'react';

interface Tournament {
  id: string;
  name: string;
  description: string;
  maxPlayers: number;
  registeredPlayers: number;
}

const TournamentList: React.FC = () => {
  const [tournaments, setTournaments] = useState<Tournament[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  return (
    <div>
      <h1>Chess Tournaments</h1>
    </div>
  );
};

export default TournamentList;