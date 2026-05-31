import { useState, useEffect } from 'react';
import { getJugadores, getEquipos, getCartas, getClub, getPlantillas, getPartidos } from '../services/api';

function Home() {
  const [stats, setStats] = useState({ jugadores: 0, equipos: 0, cartas: 0 });
  const [club, setClub] = useState(null);
  const [plantilla, setPlantilla] = useState(null);
  const [partidos, setPartidos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([getJugadores(), getEquipos(), getCartas(), getClub(), getPlantillas(), getPartidos()])
      .then(([jug, eq, car, clubRes, plantillasRes, partidosRes]) => {
        const cartas = Array.isArray(car.data) ? car.data : car.data.content || [];
        setStats({
          jugadores: Array.isArray(jug.data) ? jug.data.length : jug.data.content?.length || 0,
          equipos: eq.data.length,
          cartas: cartas.length,
        });
        setClub(clubRes.data);
        setPlantilla(plantillasRes.data[0] || null);
        setPartidos(partidosRes.data.slice(-5).reverse());
      })
      .catch(() => {})
      .finally(() => setLoading(false));
  }, []);

  return (
    <div>
      <div className="hero">
        <h1>FutManager</h1>
        <p>Gestiona tu club, cartas, plantillas, sobres, mercado y partidos</p>
      </div>

      {loading ? (
        <div className="loading">
          <div className="loading-spinner" />
          Cargando datos...
        </div>
      ) : (
        <div className="stats-grid">
          <div className="stat-card blue">
            <div className="stat-num">{stats.jugadores}</div>
            <div className="stat-label">Jugadores</div>
          </div>
          <div className="stat-card gold">
            <div className="stat-num">{stats.equipos}</div>
            <div className="stat-label">Equipos</div>
          </div>
          <div className="stat-card green">
            <div className="stat-num">{stats.cartas}</div>
            <div className="stat-label">Cartas FUT</div>
          </div>
          <div className="stat-card purple">
            <div className="stat-num">{club?.nivel || 1}</div>
            <div className="stat-label">Nivel club</div>
          </div>
        </div>
      )}

      <div className="dashboard-grid">
        <div className="card">
          <h3>Club</h3>
          <p className="dash-title">{club?.nombreClub || 'FutManager FC'}</p>
          <p>{club?.monedas || 0} monedas</p>
          <p>Nivel {club?.nivel || 1} · {club?.experiencia || 0} XP</p>
        </div>
        <div className="card">
          <h3>Plantilla principal</h3>
          <p className="dash-title">{plantilla?.nombre || 'Sin plantilla'}</p>
          <p>Media {plantilla?.mediaTotal || 0} · Quimica {plantilla?.quimica || 0}</p>
          <p>{plantilla?.cartas?.length || 0}/11 titulares</p>
        </div>
        <div className="card">
          <h3>Ultimos partidos</h3>
          {partidos.length === 0 ? <p>No hay partidos todavia.</p> : partidos.map(p => (
            <p key={p.id}>{p.plantillaNombre} {p.golesUsuario}-{p.golesRival} {p.rival}</p>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Home;
