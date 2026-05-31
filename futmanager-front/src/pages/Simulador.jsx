import { useEffect, useState } from 'react';
import { getPartidos, getPlantillas, simularPartido } from '../services/api';

function Simulador() {
  const [plantillas, setPlantillas] = useState([]);
  const [partidos, setPartidos] = useState([]);
  const [plantillaId, setPlantillaId] = useState('');
  const [resultado, setResultado] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const loadData = async () => {
    setLoading(true);
    try {
      const [resPlantillas, resPartidos] = await Promise.all([getPlantillas(), getPartidos()]);
      setPlantillas(resPlantillas.data);
      setPartidos(resPartidos.data);
      if (!plantillaId && resPlantillas.data.length > 0) {
        setPlantillaId(String(resPlantillas.data[0].id));
      }
    } catch {
      setError('Error al cargar el simulador');
    } finally {
      setLoading(false);
    }
  };

  const plantilla = plantillas.find(p => String(p.id) === String(plantillaId));

  const jugar = async () => {
    setError('');
    setResultado(null);
    try {
      const res = await simularPartido(plantillaId);
      setResultado(res.data);
      loadData();
    } catch (err) {
      setError(err.response?.data || 'No se pudo simular el partido');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Simulador de partidos</h2>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {loading ? (
        <div className="loading"><div className="loading-spinner" />Cargando...</div>
      ) : (
        <>
          <div className="card" style={{ marginBottom: '1.5rem' }}>
            <div className="filters" style={{ marginBottom: 0 }}>
              <div className="form-group">
                <label>Plantilla</label>
                <select value={plantillaId} onChange={e => setPlantillaId(e.target.value)}>
                  {plantillas.map(p => <option key={p.id} value={p.id}>{p.nombre} ({p.cartas.length}/11)</option>)}
                </select>
              </div>
              <button className="btn btn-primary" onClick={jugar} disabled={!plantillaId}>Jugar partido</button>
            </div>
          </div>

          {plantilla && (
            <div className="stats-grid">
              <div className="stat-card gold"><div className="stat-num">{plantilla.mediaTotal}</div><div className="stat-label">Media</div></div>
              <div className="stat-card green"><div className="stat-num">{plantilla.quimica}</div><div className="stat-label">Química</div></div>
              <div className="stat-card blue"><div className="stat-num">{plantilla.valoracionOfensiva}</div><div className="stat-label">Ataque</div></div>
              <div className="stat-card purple"><div className="stat-num">{plantilla.valoracionDefensiva}</div><div className="stat-label">Defensa</div></div>
            </div>
          )}

          {resultado && (
            <div className="card" style={{ marginBottom: '1.5rem' }}>
              <h3 style={{ color: 'var(--accent)', marginBottom: '1rem' }}>
                {resultado.plantillaNombre} {resultado.golesUsuario} - {resultado.golesRival} {resultado.rival}
              </h3>
              <p style={{ color: 'var(--text-muted)', marginBottom: '0.8rem' }}>{resultado.resumen}</p>
              <p><strong>Ganador:</strong> {resultado.ganador}</p>
              <p><strong>MVP:</strong> {resultado.mvp || resultado.mejoresJugadores}</p>
              <p><strong>Monedas ganadas:</strong> {resultado.monedasGanadas || 0}</p>
              <pre className="events-box">{resultado.eventos}</pre>
            </div>
          )}

          <div className="card">
            <h3 style={{ color: 'var(--accent)', marginBottom: '1rem' }}>Historial</h3>
            {partidos.length === 0 ? (
              <p style={{ color: 'var(--text-muted)' }}>Aún no hay partidos simulados.</p>
            ) : (
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Plantilla</th>
                    <th>Rival</th>
                    <th>Resultado</th>
                    <th>Ganador</th>
                  </tr>
                </thead>
                <tbody>
                  {partidos.map(p => (
                    <tr key={p.id}>
                      <td>{p.plantillaNombre}</td>
                      <td>{p.rival}</td>
                      <td>{p.golesUsuario} - {p.golesRival}</td>
                      <td>{p.ganador}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            )}
          </div>
        </>
      )}
    </div>
  );
}

export default Simulador;
