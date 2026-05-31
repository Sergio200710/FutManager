import { useState, useEffect } from 'react';
import { getEquipos, createEquipo, deleteEquipo, updateEquipo } from '../services/api';

const emptyForm = { nombre: '', liga: '', pais: '' };

function Equipos() {
  const [equipos, setEquipos] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => { loadEquipos(); }, []);

  const loadEquipos = async () => {
    setLoading(true);
    try {
      const res = await getEquipos();
      setEquipos(res.data);
    } catch {
      setError('Error al cargar equipos');
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      if (editingId) {
        await updateEquipo(editingId, form);
      } else {
        await createEquipo(form);
      }
      setForm(emptyForm);
      setEditingId(null);
      setShowModal(false);
      loadEquipos();
    } catch (err) {
      setError(err.response?.data || 'Error al guardar el equipo');
    }
  };

  const handleEdit = (eq) => {
    setForm({ nombre: eq.nombre, liga: eq.liga, pais: eq.pais });
    setEditingId(eq.id);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!confirm('¿Eliminar este equipo? También se eliminarán sus jugadores.')) return;
    try {
      await deleteEquipo(id);
      loadEquipos();
    } catch { setError('Error al eliminar'); }
  };

  const ligasBadgeColor = {
    'La Liga': '#e8c842', 'Premier League': '#00d4ff',
    'Serie A': '#ff6b6b', 'Bundesliga': '#ff8c00', default: '#888'
  };

  return (
    <div>
      <div className="page-header">
        <h2>Equipos</h2>
        <button className="btn btn-primary" onClick={() => { setForm(emptyForm); setEditingId(null); setShowModal(true); }}>
          + Nuevo Equipo
        </button>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {loading ? (
        <div className="loading"><div className="loading-spinner" />Cargando...</div>
      ) : equipos.length === 0 ? (
        <div className="empty"><div className="empty-icon">🏟️</div>No hay equipos todavía</div>
      ) : (
        <div className="card">
          <table className="data-table">
            <thead>
              <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Liga</th>
                <th>País</th>
                <th>Jugadores</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {equipos.map(eq => (
                <tr key={eq.id}>
                  <td style={{ color: 'var(--text-muted)' }}>{eq.id}</td>
                  <td style={{ fontWeight: 600 }}>{eq.nombre}</td>
                  <td>
                    <span className="badge" style={{
                      background: `${ligasBadgeColor[eq.liga] || '#888'}22`,
                      color: ligasBadgeColor[eq.liga] || '#888',
                      border: `1px solid ${ligasBadgeColor[eq.liga] || '#888'}44`
                    }}>
                      {eq.liga || '—'}
                    </span>
                  </td>
                  <td>{eq.pais || '—'}</td>
                  <td>
                    <span className="badge badge-skipped">
                      {eq.numeroJugadores || 0} jugadores
                    </span>
                  </td>
                  <td>
                    <div className="actions">
                      <button className="btn btn-edit btn-sm" onClick={() => handleEdit(eq)}>✏️ Editar</button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(eq.id)}>🗑️ Eliminar</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h3>{editingId ? 'Editar Equipo' : 'Nuevo Equipo'}</h3>
            {error && <div className="alert alert-error">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="form-grid">
                <div className="form-group">
                  <label>Nombre *</label>
                  <input name="nombre" value={form.nombre} onChange={handleChange} required />
                </div>
                <div className="form-group">
                  <label>Liga</label>
                  <input name="liga" value={form.liga} onChange={handleChange} placeholder="La Liga, Premier League..." />
                </div>
                <div className="form-group">
                  <label>País</label>
                  <input name="pais" value={form.pais} onChange={handleChange} placeholder="España, Inglaterra..." />
                </div>
              </div>
              <div className="form-actions">
                <button type="submit" className="btn btn-primary">{editingId ? 'Actualizar' : 'Crear'}</button>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>Cancelar</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}

export default Equipos;
