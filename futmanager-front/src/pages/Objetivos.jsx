import { useEffect, useState } from 'react';
import { completarObjetivo, getClub, getObjetivos } from '../services/api';

function Objetivos() {
  const [objetivos, setObjetivos] = useState([]);
  const [club, setClub] = useState(null);
  const [error, setError] = useState('');
  const [mensaje, setMensaje] = useState('');

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    setError('');
    try {
      const [resObjetivos, resClub] = await Promise.all([getObjetivos(), getClub()]);
      setObjetivos(resObjetivos.data);
      setClub(resClub.data);
    } catch {
      setError('No se pudieron cargar los objetivos');
    }
  };

  const completar = async (id) => {
    setMensaje('');
    setError('');
    try {
      await completarObjetivo(id);
      setMensaje('Objetivo completado y recompensa recibida');
      loadData();
    } catch (err) {
      setError(err.response?.data || 'No se pudo completar el objetivo');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Objetivos</h2>
        <div className="coin-pill">{club?.monedas || 0} monedas</div>
      </div>
      {error && <div className="alert alert-error">{error}</div>}
      {mensaje && <div className="alert alert-success">{mensaje}</div>}
      <div className="card-list">
        {objetivos.map(objetivo => (
          <div key={objetivo.id} className="card row-card">
            <div>
              <h3>{objetivo.descripcion}</h3>
              <p>{objetivo.tipo} · {objetivo.recompensaMonedas} monedas</p>
            </div>
            <button className="btn btn-primary" disabled={objetivo.completado} onClick={() => completar(objetivo.id)}>
              {objetivo.completado ? 'Completado' : 'Completar'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Objetivos;
