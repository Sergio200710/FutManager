import { useEffect, useState } from 'react';
import { cambiarTransferible, getCartasClub, venderCartaClub } from '../services/api';

function MisCartas() {
  const [cartas, setCartas] = useState([]);
  const [mensaje, setMensaje] = useState('');
  const [error, setError] = useState('');

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    setError('');
    try {
      const res = await getCartasClub();
      setCartas(res.data);
    } catch {
      setError('No se pudieron cargar tus cartas');
    }
  };

  const vender = async (id) => {
    setMensaje('');
    setError('');
    try {
      await venderCartaClub(id);
      setMensaje('Carta vendida');
      loadData();
    } catch (err) {
      setError(err.response?.data || 'No se pudo vender la carta');
    }
  };

  const toggleTransferible = async (carta) => {
    try {
      await cambiarTransferible(carta.id, !carta.transferible);
      loadData();
    } catch {
      setError('No se pudo cambiar el estado transferible');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Mis cartas</h2>
      </div>
      {error && <div className="alert alert-error">{error}</div>}
      {mensaje && <div className="alert alert-success">{mensaje}</div>}
      {cartas.length === 0 ? (
        <div className="empty"><div className="empty-icon">FUT</div>No tienes cartas en el club.</div>
      ) : (
        <div className="jugadores-grid">
          {cartas.map(carta => (
            <div key={carta.id} className="fut-card oro">
              <div className="rating">{carta.media}</div>
              <div className="pos-badge">{carta.posicion}</div>
              {carta.imagenJugador && <img className="player-img" src={carta.imagenJugador} alt={carta.nombreJugador || carta.jugadorNombre} />}
              <div className="jugador-name">{carta.nombreJugador || carta.jugadorNombre || carta.club}</div>
              <div className="equipo-name">{carta.club}</div>
              <div className="equipo-name">{carta.transferible ? 'Transferible' : 'No transferible'}</div>
              <div className="card-actions">
                <button className="btn btn-secondary btn-sm" onClick={() => toggleTransferible(carta)}>Transferible</button>
                <button className="btn btn-danger btn-sm" onClick={() => vender(carta.id)}>Vender</button>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default MisCartas;
