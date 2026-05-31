import { useState, useEffect } from 'react';
import { getJugadores, createJugador, deleteJugador, updateJugador, getEquipos } from '../services/api';

const POSICIONES = ['POR', 'LD', 'LI', 'CB', 'MC', 'MCD', 'MCO', 'ED', 'EI', 'DC', 'SD'];

function getCardClass(media) {
  if (media >= 87) return 'especial';
  if (media >= 80) return 'oro';
  if (media >= 65) return 'plata';
  return 'bronce';
}

const emptyForm = {
  nombre: '', posicion: '', nacionalidad: '', edad: '', media: '', ritmo: '', tiro: '',
  pase: '', regate: '', defensa: '', fisico: '', equipoId: ''
};

const toNumberOrNull = (value) => value === '' ? null : Number(value);

function Jugadores() {
  const [jugadores, setJugadores] = useState([]);
  const [equipos, setEquipos] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [filtroPos, setFiltroPos] = useState('');
  const [filtroMedia, setFiltroMedia] = useState('');

  useEffect(() => { loadData(); }, []);

  const loadData = async (params) => {
    setLoading(true);
    try {
      const [resJug, resEq] = await Promise.all([
        getJugadores(params),
        getEquipos()
      ]);
      setJugadores(resJug.data);
      setEquipos(resEq.data);
    } catch {
      setError('Error al cargar datos');
    } finally {
      setLoading(false);
    }
  };

  const handleFiltrar = () => {
    const params = {};
    if (filtroPos) params.posicion = filtroPos;
    if (filtroMedia) params.mediaMinima = filtroMedia;
    loadData(params);
  };

  const handleLimpiarFiltros = () => {
    setFiltroPos('');
    setFiltroMedia('');
    loadData();
  };

  const handleChange = (e) => {
    if (e.target.name === 'equipoId') {
      setForm({ ...form, equipoId: e.target.value });
    } else {
      setForm({ ...form, [e.target.name]: e.target.value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const payload = {
        ...form,
        edad: Number(form.edad),
        media: Number(form.media),
        ritmo: toNumberOrNull(form.ritmo),
        tiro: toNumberOrNull(form.tiro),
        pase: toNumberOrNull(form.pase),
        regate: toNumberOrNull(form.regate),
        defensa: toNumberOrNull(form.defensa),
        fisico: toNumberOrNull(form.fisico),
        equipoId: Number(form.equipoId),
      };
      if (editingId) {
        await updateJugador(editingId, payload);
      } else {
        await createJugador(payload);
      }
      setForm(emptyForm);
      setEditingId(null);
      setShowModal(false);
      loadData();
    } catch (err) {
      setError(err.response?.data || 'Error al guardar el jugador');
    }
  };

  const handleEdit = (jug) => {
    setForm({
      nombre: jug.nombre,
      posicion: jug.posicion,
      nacionalidad: jug.nacionalidad || '',
      edad: jug.edad || '',
      media: jug.media,
      ritmo: jug.ritmo || '',
      tiro: jug.tiro || '',
      pase: jug.pase || '',
      regate: jug.regate || '',
      defensa: jug.defensa || '',
      fisico: jug.fisico || '',
      equipoId: jug.equipoId || ''
    });
    setEditingId(jug.id);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!confirm('¿Eliminar este jugador?')) return;
    try {
      await deleteJugador(id);
      loadData();
    } catch {
      setError('Error al eliminar');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Jugadores</h2>
        <button className="btn btn-primary" onClick={() => { setForm(emptyForm); setEditingId(null); setShowModal(true); }}>
          + Nuevo Jugador
        </button>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {/* Filtros */}
      <div className="filters">
        <div className="form-group">
          <label>Posición</label>
          <select value={filtroPos} onChange={e => setFiltroPos(e.target.value)}>
            <option value="">Todas</option>
            {POSICIONES.map(p => <option key={p} value={p}>{p}</option>)}
          </select>
        </div>
        <div className="form-group">
          <label>Media mínima</label>
          <input type="number" min="1" max="99" placeholder="ej: 80"
            value={filtroMedia} onChange={e => setFiltroMedia(e.target.value)} />
        </div>
        <div style={{ display: 'flex', gap: '0.5rem', alignItems: 'flex-end' }}>
          <button className="btn btn-primary btn-sm" onClick={handleFiltrar}>Filtrar</button>
          <button className="btn btn-secondary btn-sm" onClick={handleLimpiarFiltros}>Limpiar</button>
        </div>
      </div>

      {/* Grid de cartas */}
      {loading ? (
        <div className="loading"><div className="loading-spinner" />Cargando...</div>
      ) : jugadores.length === 0 ? (
        <div className="empty"><div className="empty-icon">⚽</div>No hay jugadores todavía</div>
      ) : (
        <div className="jugadores-grid">
          {jugadores.map(jug => (
            <div key={jug.id} className={`fut-card ${getCardClass(jug.media)}`}>
              <div className="rating">{jug.media}</div>
              <div className="pos-badge">{jug.posicion}</div>
              <div className="jugador-name">{jug.nombre}</div>
              <div className="stats-row">
                <span><b>{jug.ritmo}</b>RIT</span>
                <span><b>{jug.tiro}</b>TIR</span>
                <span><b>{jug.pase}</b>PAS</span>
                <span><b>{jug.regate}</b>REG</span>
                <span><b>{jug.defensa}</b>DEF</span>
                <span><b>{jug.fisico}</b>FIS</span>
              </div>
              <div className="equipo-name">{jug.equipoNombre || 'Sin equipo'}</div>
              <div className="card-actions">
                <button className="btn btn-edit btn-sm" onClick={() => handleEdit(jug)}>✏️</button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(jug.id)}>🗑️</button>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Modal formulario */}
      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h3>{editingId ? 'Editar Jugador' : 'Nuevo Jugador'}</h3>
            {error && <div className="alert alert-error">{error}</div>}
            <form onSubmit={handleSubmit}>
              <div className="form-grid">
                <div className="form-group">
                  <label>Nombre *</label>
                  <input name="nombre" value={form.nombre} onChange={handleChange} required />
                </div>
                <div className="form-group">
                  <label>Posición *</label>
                  <select name="posicion" value={form.posicion} onChange={handleChange} required>
                    <option value="">Seleccionar...</option>
                    {POSICIONES.map(p => <option key={p} value={p}>{p}</option>)}
                  </select>
                </div>
                <div className="form-group">
                  <label>Nacionalidad *</label>
                  <input name="nacionalidad" value={form.nacionalidad} onChange={handleChange} required />
                </div>
                <div className="form-group">
                  <label>Edad *</label>
                  <input type="number" name="edad" min="16" max="45" value={form.edad} onChange={handleChange} required />
                </div>
                <div className="form-group">
                  <label>Media (1-99) *</label>
                  <input type="number" name="media" min="1" max="99" value={form.media} onChange={handleChange} required />
                </div>
                <div className="form-group">
                  <label>Ritmo</label>
                  <input type="number" name="ritmo" min="1" max="99" value={form.ritmo} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Tiro</label>
                  <input type="number" name="tiro" min="1" max="99" value={form.tiro} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Pase</label>
                  <input type="number" name="pase" min="1" max="99" value={form.pase} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Regate</label>
                  <input type="number" name="regate" min="1" max="99" value={form.regate} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Defensa</label>
                  <input type="number" name="defensa" min="1" max="99" value={form.defensa} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Físico</label>
                  <input type="number" name="fisico" min="1" max="99" value={form.fisico} onChange={handleChange} />
                </div>
                <div className="form-group">
                  <label>Equipo *</label>
                  <select name="equipoId" value={form.equipoId || ''} onChange={handleChange} required>
                    <option value="">Seleccionar equipo...</option>
                    {equipos.map(eq => <option key={eq.id} value={eq.id}>{eq.nombre}</option>)}
                  </select>
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

export default Jugadores;
