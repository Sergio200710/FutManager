import { useState, useEffect } from 'react';
import { buscarCartas, createCarta, deleteCarta, updateCarta, importarCartasApi, getJugadores, importarTodoReal } from '../services/api';

const RAREZAS = ['Bronce comun', 'Bronce raro', 'Plata comun', 'Plata rara', 'Oro comun', 'Oro raro', 'Especial', 'TOTW', 'TOTS', 'Icono', 'Héroe', 'Oro Rare', 'Oro', 'Plata Rare', 'Plata', 'Bronce', 'IF', 'TOTY', 'Promesa'];
const ESTILOS = ['Cazador', 'Sombra', 'Motor', 'Bestia', 'Ancla', 'Halcón', 'Maestro', 'Catalyst', 'Guante'];
const emptyForm = {
  tipoCarta: 'Oro', nombreJugador: '', rareza: '', media: '', ritmo: '', tiro: '', pase: '', regate: '',
  defensa: '', fisico: '', imagenJugador: '', imagenEscudo: '', imagenBandera: '',
  colorCarta: '#facc15', liga: '', club: '', nacionalidad: '', posicion: '',
  precioMonedas: '', estiloCarta: '', jugadorId: ''
};

function getRarezaClass(rareza) {
  if (!rareza) return 'oro';
  const r = rareza.toLowerCase();
  if (r.includes('especial') || r.includes('toty') || r.includes('promesa')) return 'especial';
  if (r.includes('if')) return 'if';
  if (r.includes('oro')) return 'oro';
  if (r.includes('plata')) return 'plata';
  return 'bronce';
}

const num = (value) => value === '' ? null : Number(value);

function Cartas() {
  const [cartas, setCartas] = useState([]);
  const [jugadores, setJugadores] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [mensaje, setMensaje] = useState('');
  const [filtros, setFiltros] = useState({
    nombre: '', liga: '', club: '', nacionalidad: '', posicion: '', rareza: '',
    mediaMin: '', mediaMax: '', precioMin: '', precioMax: '', sortBy: 'media', direction: 'desc'
  });
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(1);

  // eslint-disable-next-line react-hooks/exhaustive-deps
  useEffect(() => { loadData(); }, [page]);

  const loadData = async () => {
    setLoading(true);
    try {
      const params = { ...filtros, page, size: 20 };
      Object.keys(params).forEach(key => {
        if (params[key] === '') delete params[key];
      });
      const [resCartas, resJug] = await Promise.all([buscarCartas(params), getJugadores({ page: 0, size: 100 })]);
      const data = resCartas.data;
      setCartas(Array.isArray(data) ? data : data.content);
      setTotalPages(Array.isArray(data) ? 1 : data.totalPages || 1);
      setJugadores(Array.isArray(resJug.data) ? resJug.data : resJug.data.content);
    } catch {
      setError('Error al cargar datos');
    } finally {
      setLoading(false);
    }
  };

  const handleFiltro = (e) => {
    setFiltros({ ...filtros, [e.target.name]: e.target.value });
  };

  const aplicarFiltros = () => {
    setPage(0);
    loadData();
  };

  const limpiarFiltros = () => {
    setFiltros({ nombre: '', liga: '', club: '', nacionalidad: '', posicion: '', rareza: '', mediaMin: '', mediaMax: '', precioMin: '', precioMax: '', sortBy: 'media', direction: 'desc' });
    setPage(0);
    setTimeout(loadData, 0);
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      const payload = {
        ...form,
        media: num(form.media),
        ritmo: num(form.ritmo),
        tiro: num(form.tiro),
        pase: num(form.pase),
        regate: num(form.regate),
        defensa: num(form.defensa),
        fisico: num(form.fisico),
        precioMonedas: num(form.precioMonedas),
        jugadorId: form.jugadorId ? Number(form.jugadorId) : null,
      };
      if (editingId) {
        await updateCarta(editingId, payload);
      } else {
        await createCarta(payload);
      }
      setForm(emptyForm);
      setEditingId(null);
      setShowModal(false);
      loadData();
    } catch (err) {
      setError(err.response?.data || 'Error al guardar la carta');
    }
  };

  const handleEdit = (carta) => {
    setForm({
      tipoCarta: carta.tipoCarta || 'Oro',
      nombreJugador: carta.nombreJugador || '',
      rareza: carta.rareza || '',
      media: carta.media || '',
      ritmo: carta.ritmo || '',
      tiro: carta.tiro || '',
      pase: carta.pase || '',
      regate: carta.regate || '',
      defensa: carta.defensa || '',
      fisico: carta.fisico || '',
      imagenJugador: carta.imagenJugador || '',
      imagenEscudo: carta.imagenEscudo || '',
      imagenBandera: carta.imagenBandera || '',
      colorCarta: carta.colorCarta || '#facc15',
      liga: carta.liga || '',
      club: carta.club || '',
      nacionalidad: carta.nacionalidad || '',
      posicion: carta.posicion || '',
      precioMonedas: carta.precioMonedas || '',
      estiloCarta: carta.estiloCarta || '',
      jugadorId: carta.jugadorId || ''
    });
    setEditingId(carta.id);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!confirm('¿Eliminar esta carta?')) return;
    try {
      await deleteCarta(id);
      loadData();
    } catch {
      setError('Error al eliminar');
    }
  };

  const handleImportar = async () => {
    setMensaje('');
    setError('');
    try {
      const res = await importarCartasApi();
      setMensaje(`Cartas importadas: ${res.data.length}`);
      loadData();
    } catch {
      setError('No se pudieron importar cartas');
    }
  };

  const handleImportarTodo = async () => {
    setMensaje('');
    setError('');
    setLoading(true);
    try {
      const res = await importarTodoReal();
      setMensaje(`Importacion completada: ${res.data.jugadores} jugadores y ${res.data.cartas} cartas`);
      setPage(0);
      loadData();
    } catch {
      setError('No se pudo ejecutar el importador masivo');
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Cartas FUT</h2>
        <div className="actions">
          <button className="btn btn-secondary" onClick={handleImportarTodo}>Importar Todo</button>
          <button className="btn btn-secondary" onClick={handleImportar}>Importar API</button>
          <button className="btn btn-primary" onClick={() => { setForm(emptyForm); setEditingId(null); setShowModal(true); }}>
            + Nueva Carta
          </button>
        </div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}
      {mensaje && <div className="alert alert-success">{mensaje}</div>}

      <div className="filters-panel">
        <input name="nombre" placeholder="Buscar jugador" value={filtros.nombre} onChange={handleFiltro} />
        <input name="liga" placeholder="Liga" value={filtros.liga} onChange={handleFiltro} />
        <input name="club" placeholder="Equipo" value={filtros.club} onChange={handleFiltro} />
        <input name="nacionalidad" placeholder="Nacionalidad" value={filtros.nacionalidad} onChange={handleFiltro} />
        <input name="posicion" placeholder="Posición" value={filtros.posicion} onChange={handleFiltro} />
        <select name="rareza" value={filtros.rareza} onChange={handleFiltro}>
          <option value="">Todas las rarezas</option>
          {RAREZAS.map(r => <option key={r} value={r}>{r}</option>)}
        </select>
        <input type="number" name="mediaMin" placeholder="Media mínima" value={filtros.mediaMin} onChange={handleFiltro} />
        <input type="number" name="mediaMax" placeholder="Media máxima" value={filtros.mediaMax} onChange={handleFiltro} />
        <input type="number" name="precioMin" placeholder="Precio mínimo" value={filtros.precioMin} onChange={handleFiltro} />
        <input type="number" name="precioMax" placeholder="Precio máximo" value={filtros.precioMax} onChange={handleFiltro} />
        <select name="sortBy" value={filtros.sortBy} onChange={handleFiltro}>
          <option value="media">Ordenar por media</option>
          <option value="nombreJugador">Ordenar por nombre</option>
          <option value="precioMonedas">Ordenar por precio</option>
          <option value="posicion">Ordenar por posición</option>
        </select>
        <select name="direction" value={filtros.direction} onChange={handleFiltro}>
          <option value="desc">Mayor a menor</option>
          <option value="asc">Menor a mayor</option>
        </select>
        <button className="btn btn-primary" onClick={aplicarFiltros}>Filtrar</button>
        <button className="btn btn-secondary" onClick={limpiarFiltros}>Limpiar</button>
      </div>

      {loading ? (
        <div className="loading"><div className="loading-spinner" />Cargando...</div>
      ) : cartas.length === 0 ? (
        <div className="empty"><div className="empty-icon">FUT</div>No se encontraron jugadores con esos filtros.</div>
      ) : (
        <div className="jugadores-grid">
          {cartas.map(carta => (
            <div key={carta.id} className={`fut-card ${getRarezaClass(carta.rareza)}`} style={{ borderColor: carta.colorCarta || undefined }}>
              <div className="rating">{carta.media || '—'}</div>
              <div className="pos-badge">{carta.posicion || carta.jugadorPosicion || 'POS'}</div>
              {carta.imagenJugador && <img className="player-img" src={carta.imagenJugador} alt={carta.jugadorNombre || carta.club} />}
              <div className="jugador-name">{carta.nombreJugador || carta.jugadorNombre || `${carta.club || 'Carta'} ${carta.posicion || ''}`}</div>
              <div className="stats-row">
                <span><b>{carta.ritmo}</b>RIT</span>
                <span><b>{carta.tiro}</b>TIR</span>
                <span><b>{carta.pase}</b>PAS</span>
                <span><b>{carta.regate}</b>REG</span>
                <span><b>{carta.defensa}</b>DEF</span>
                <span><b>{carta.fisico}</b>FIS</span>
              </div>
              <div className="mini-assets">
                {carta.imagenEscudo && <img src={carta.imagenEscudo} alt="escudo" />}
                {carta.imagenBandera && <img src={carta.imagenBandera} alt="bandera" />}
              </div>
              <div className="equipo-name">{carta.club || 'Sin club'} · {carta.nacionalidad || '—'}</div>
              <div className="equipo-name">{carta.rareza} · {carta.precioMonedas || 0} monedas</div>
              <div className="card-actions">
                <button className="btn btn-edit btn-sm" onClick={() => handleEdit(carta)}>Editar</button>
                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(carta.id)}>Borrar</button>
              </div>
            </div>
          ))}
        </div>
      )}

      <div className="pagination">
        <button className="btn btn-secondary" disabled={page === 0} onClick={() => setPage(page - 1)}>Anterior</button>
        <span>Página {page + 1} de {totalPages}</span>
        <button className="btn btn-secondary" disabled={page + 1 >= totalPages} onClick={() => setPage(page + 1)}>Siguiente</button>
      </div>

      {showModal && (
        <div className="modal-overlay" onClick={() => setShowModal(false)}>
          <div className="modal" onClick={e => e.stopPropagation()}>
            <h3>{editingId ? 'Editar Carta' : 'Nueva Carta FUT'}</h3>
            <form onSubmit={handleSubmit}>
              <div className="form-grid">
                <div className="form-group"><label>Tipo</label><input name="tipoCarta" value={form.tipoCarta} onChange={handleChange} required /></div>
                <div className="form-group"><label>Nombre jugador</label><input name="nombreJugador" value={form.nombreJugador} onChange={handleChange} /></div>
                <div className="form-group"><label>Rareza</label><select name="rareza" value={form.rareza} onChange={handleChange} required><option value="">Seleccionar...</option>{RAREZAS.map(r => <option key={r} value={r}>{r}</option>)}</select></div>
                <div className="form-group"><label>Media</label><input type="number" name="media" min="1" max="99" value={form.media} onChange={handleChange} required /></div>
                <div className="form-group"><label>Ritmo</label><input type="number" name="ritmo" min="1" max="99" value={form.ritmo} onChange={handleChange} /></div>
                <div className="form-group"><label>Tiro</label><input type="number" name="tiro" min="1" max="99" value={form.tiro} onChange={handleChange} /></div>
                <div className="form-group"><label>Pase</label><input type="number" name="pase" min="1" max="99" value={form.pase} onChange={handleChange} /></div>
                <div className="form-group"><label>Regate</label><input type="number" name="regate" min="1" max="99" value={form.regate} onChange={handleChange} /></div>
                <div className="form-group"><label>Defensa</label><input type="number" name="defensa" min="1" max="99" value={form.defensa} onChange={handleChange} /></div>
                <div className="form-group"><label>Físico</label><input type="number" name="fisico" min="1" max="99" value={form.fisico} onChange={handleChange} /></div>
                <div className="form-group"><label>Color</label><input name="colorCarta" value={form.colorCarta} onChange={handleChange} required /></div>
                <div className="form-group"><label>Liga</label><input name="liga" value={form.liga} onChange={handleChange} /></div>
                <div className="form-group"><label>Club</label><input name="club" value={form.club} onChange={handleChange} /></div>
                <div className="form-group"><label>Nacionalidad</label><input name="nacionalidad" value={form.nacionalidad} onChange={handleChange} /></div>
                <div className="form-group"><label>Posición</label><input name="posicion" value={form.posicion} onChange={handleChange} /></div>
                <div className="form-group"><label>Precio</label><input type="number" name="precioMonedas" min="0" value={form.precioMonedas} onChange={handleChange} /></div>
                <div className="form-group"><label>Estilo</label><select name="estiloCarta" value={form.estiloCarta} onChange={handleChange} required><option value="">Seleccionar...</option>{ESTILOS.map(e => <option key={e} value={e}>{e}</option>)}</select></div>
                <div className="form-group"><label>Jugador</label><select name="jugadorId" value={form.jugadorId || ''} onChange={handleChange}><option value="">Sin jugador</option>{jugadores.map(j => <option key={j.id} value={j.id}>{j.nombre} ({j.posicion})</option>)}</select></div>
                <div className="form-group"><label>Imagen jugador</label><input name="imagenJugador" value={form.imagenJugador} onChange={handleChange} /></div>
                <div className="form-group"><label>Escudo</label><input name="imagenEscudo" value={form.imagenEscudo} onChange={handleChange} /></div>
                <div className="form-group"><label>Bandera</label><input name="imagenBandera" value={form.imagenBandera} onChange={handleChange} /></div>
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

export default Cartas;
