import { useEffect, useState } from 'react';
import { evolucionarCarta, getCartas, getEvoluciones } from '../services/api';

function Evoluciones() {
  const [cartas, setCartas] = useState([]);
  const [evoluciones, setEvoluciones] = useState([]);
  const [cartaId, setCartaId] = useState('');
  const [error, setError] = useState('');
  const [mensaje, setMensaje] = useState('');

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    setError('');
    try {
      const [resCartas, resEvoluciones] = await Promise.all([getCartas({ page: 0, size: 100 }), getEvoluciones()]);
      setCartas(Array.isArray(resCartas.data) ? resCartas.data : resCartas.data.content || []);
      setEvoluciones(resEvoluciones.data);
    } catch {
      setError('No se pudieron cargar las evoluciones');
    }
  };

  const evolucionar = async () => {
    if (!cartaId) return;
    setMensaje('');
    setError('');
    try {
      await evolucionarCarta(cartaId);
      setMensaje('Carta evolucionada');
      setCartaId('');
      loadData();
    } catch (err) {
      setError(err.response?.data || 'No se pudo evolucionar la carta');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Evoluciones</h2>
      </div>
      {error && <div className="alert alert-error">{error}</div>}
      {mensaje && <div className="alert alert-success">{mensaje}</div>}
      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <div className="filters" style={{ marginBottom: 0 }}>
          <div className="form-group">
            <label>Carta</label>
            <select value={cartaId} onChange={e => setCartaId(e.target.value)}>
              <option value="">Seleccionar carta...</option>
              {cartas.map(carta => (
                <option key={carta.id} value={carta.id}>
                  {carta.media} {carta.posicion} - {carta.nombreJugador || carta.jugadorNombre || carta.club}
                </option>
              ))}
            </select>
          </div>
          <button className="btn btn-primary" onClick={evolucionar}>Aplicar mejora</button>
        </div>
      </div>
      <div className="card-list">
        {evoluciones.map(evo => (
          <div key={evo.id} className="card row-card">
            <div>
              <h3>{evo.nombreEvolucion}</h3>
              <p>+{evo.mejoraMedia} media · +{evo.mejoraRitmo} ritmo · +{evo.mejoraTiro} tiro · +{evo.mejoraPase} pase</p>
            </div>
            <span className="badge badge-passed">{evo.completada ? 'Completada' : 'Pendiente'}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Evoluciones;
