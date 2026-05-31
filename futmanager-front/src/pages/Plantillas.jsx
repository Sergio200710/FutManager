import { useEffect, useState } from 'react';
import {
  addCartaPlantilla,
  createPlantilla,
  deletePlantilla,
  getCartas,
  getPlantillas,
  removeCartaPlantilla
} from '../services/api';

function Plantillas() {
  const [plantillas, setPlantillas] = useState([]);
  const [cartas, setCartas] = useState([]);
  const [nombre, setNombre] = useState('');
  const [plantillaId, setPlantillaId] = useState('');
  const [cartaId, setCartaId] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadData = async () => {
    setLoading(true);
    setError('');
    try {
      const [resPlantillas, resCartas] = await Promise.all([getPlantillas(), getCartas()]);
      setPlantillas(resPlantillas.data);
      setCartas(resCartas.data);
      if (!plantillaId && resPlantillas.data.length > 0) {
        setPlantillaId(String(resPlantillas.data[0].id));
      }
    } catch {
      setError('Error al cargar plantillas');
    } finally {
      setLoading(false);
    }
  };

  const plantillaActual = plantillas.find(p => String(p.id) === String(plantillaId));

  const handleCrear = async (e) => {
    e.preventDefault();
    if (!nombre.trim()) return;
    try {
      const res = await createPlantilla({ nombre });
      setNombre('');
      setPlantillaId(String(res.data.id));
      loadData();
    } catch {
      setError('No se pudo crear la plantilla');
    }
  };

  const handleEliminarPlantilla = async () => {
    if (!plantillaActual || !confirm('¿Eliminar esta plantilla?')) return;
    try {
      await deletePlantilla(plantillaActual.id);
      setPlantillaId('');
      loadData();
    } catch {
      setError('No se pudo eliminar la plantilla');
    }
  };

  const handleAddCarta = async () => {
    if (!plantillaActual || !cartaId) return;
    try {
      await addCartaPlantilla(plantillaActual.id, cartaId);
      setCartaId('');
      loadData();
    } catch (err) {
      setError(err.response?.data || 'No se pudo añadir la carta');
    }
  };

  const handleRemoveCarta = async (id) => {
    if (!plantillaActual) return;
    try {
      await removeCartaPlantilla(plantillaActual.id, id);
      loadData();
    } catch {
      setError('No se pudo quitar la carta');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Plantillas</h2>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      <div className="card" style={{ marginBottom: '1.5rem' }}>
        <form onSubmit={handleCrear} className="filters" style={{ marginBottom: 0 }}>
          <div className="form-group">
            <label>Nombre de plantilla</label>
            <input value={nombre} onChange={e => setNombre(e.target.value)} placeholder="Mi Ultimate Team" />
          </div>
          <button className="btn btn-primary" type="submit">Crear plantilla</button>
        </form>
      </div>

      {loading ? (
        <div className="loading"><div className="loading-spinner" />Cargando...</div>
      ) : plantillas.length === 0 ? (
        <div className="empty"><div className="empty-icon">11</div>Crea tu primera plantilla</div>
      ) : (
        <>
          <div className="filters">
            <div className="form-group">
              <label>Plantilla</label>
              <select value={plantillaId} onChange={e => setPlantillaId(e.target.value)}>
                {plantillas.map(p => <option key={p.id} value={p.id}>{p.nombre}</option>)}
              </select>
            </div>
            <div className="form-group">
              <label>Carta</label>
              <select value={cartaId} onChange={e => setCartaId(e.target.value)}>
                <option value="">Seleccionar carta...</option>
                {cartas.map(c => (
                  <option key={c.id} value={c.id}>
                    {c.media} {c.posicion || c.jugadorPosicion} - {c.jugadorNombre || c.club}
                  </option>
                ))}
              </select>
            </div>
            <button className="btn btn-primary" onClick={handleAddCarta}>Añadir carta</button>
            <button className="btn btn-danger" onClick={handleEliminarPlantilla}>Eliminar plantilla</button>
          </div>

          {plantillaActual && (
            <>
              <div className="stats-grid">
                <div className="stat-card gold"><div className="stat-num">{plantillaActual.mediaTotal}</div><div className="stat-label">Media</div></div>
                <div className="stat-card green"><div className="stat-num">{plantillaActual.quimica}</div><div className="stat-label">Química</div></div>
                <div className="stat-card blue"><div className="stat-num">{plantillaActual.valoracionOfensiva}</div><div className="stat-label">Ataque</div></div>
                <div className="stat-card purple"><div className="stat-num">{plantillaActual.valoracionDefensiva}</div><div className="stat-label">Defensa</div></div>
              </div>

              <div className="page-header">
                <h2>{plantillaActual.nombre} ({plantillaActual.cartas.length}/11)</h2>
              </div>

              <div className="jugadores-grid">
                {plantillaActual.cartas.map(carta => (
                  <div key={carta.id} className="fut-card oro">
                    <div className="rating">{carta.media}</div>
                    <div className="pos-badge">{carta.posicion || carta.jugadorPosicion}</div>
                    {carta.imagenJugador && <img className="player-img" src={carta.imagenJugador} alt={carta.jugadorNombre || carta.club} />}
                    <div className="jugador-name">{carta.nombreJugador || carta.jugadorNombre || carta.club}</div>
                    <div className="stats-row">
                      <span><b>{carta.ritmo}</b>RIT</span>
                      <span><b>{carta.tiro}</b>TIR</span>
                      <span><b>{carta.defensa}</b>DEF</span>
                    </div>
                    <div className="equipo-name">{carta.club}</div>
                    <div className="card-actions">
                      <button className="btn btn-danger btn-sm" onClick={() => handleRemoveCarta(carta.id)}>Quitar</button>
                    </div>
                  </div>
                ))}
              </div>
            </>
          )}
        </>
      )}
    </div>
  );
}

export default Plantillas;
