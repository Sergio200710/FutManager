import { useEffect, useState } from 'react';
import { comprarCarta, getMercadoCartas, getMonedero, venderCarta } from '../services/api';

function Mercado() {
  const [cartas, setCartas] = useState([]);
  const [monedas, setMonedas] = useState(0);
  const [mensaje, setMensaje] = useState('');
  const [error, setError] = useState('');
  const [filtros, setFiltros] = useState({ nombre: '', posicion: '', mediaMin: '', liga: '', club: '', rareza: '' });

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    setError('');
    try {
      const params = { ...filtros, page: 0, size: 30 };
      Object.keys(params).forEach(key => { if (params[key] === '') delete params[key]; });
      const [resCartas, resMonedero] = await Promise.all([getMercadoCartas(params), getMonedero()]);
      setCartas(resCartas.data.content || []);
      setMonedas(resMonedero.data.monedas);
    } catch {
      setError('No se pudo cargar el mercado');
    }
  };

  const handleFiltro = (e) => setFiltros({ ...filtros, [e.target.name]: e.target.value });

  const operar = async (accion, cartaId) => {
    setMensaje('');
    setError('');
    try {
      const res = accion === 'comprar' ? await comprarCarta(cartaId) : await venderCarta(cartaId);
      setMonedas(res.data.monedas);
      setMensaje(accion === 'comprar' ? 'Carta comprada' : 'Carta vendida');
    } catch (err) {
      setError(err.response?.data || 'Operacion no disponible');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Mercado</h2>
        <div className="coin-pill">{monedas} monedas</div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}
      {mensaje && <div className="alert alert-success">{mensaje}</div>}

      <div className="filters-panel">
        <input name="nombre" placeholder="Nombre" value={filtros.nombre} onChange={handleFiltro} />
        <input name="posicion" placeholder="Posicion" value={filtros.posicion} onChange={handleFiltro} />
        <input type="number" name="mediaMin" placeholder="Media minima" value={filtros.mediaMin} onChange={handleFiltro} />
        <input name="liga" placeholder="Liga" value={filtros.liga} onChange={handleFiltro} />
        <input name="club" placeholder="Club" value={filtros.club} onChange={handleFiltro} />
        <input name="rareza" placeholder="Rareza" value={filtros.rareza} onChange={handleFiltro} />
        <button className="btn btn-primary" onClick={loadData}>Filtrar</button>
      </div>

      <div className="jugadores-grid">
        {cartas.map(carta => (
          <div key={carta.id} className="fut-card oro">
            <div className="rating">{carta.media}</div>
            <div className="pos-badge">{carta.posicion || carta.jugadorPosicion}</div>
            {carta.imagenJugador && <img className="player-img" src={carta.imagenJugador} alt={carta.nombreJugador || carta.jugadorNombre} />}
            <div className="jugador-name">{carta.nombreJugador || carta.jugadorNombre || carta.club}</div>
            <div className="equipo-name">{carta.club}</div>
            <div className="equipo-name">{carta.precioMonedas || 0} monedas</div>
            <div className="card-actions">
              <button className="btn btn-primary btn-sm" onClick={() => operar('comprar', carta.id)}>Comprar</button>
              <button className="btn btn-secondary btn-sm" onClick={() => operar('vender', carta.id)}>Vender</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Mercado;
