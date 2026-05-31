import { useEffect, useState } from 'react';
import { abrirSobre, getMonedero, venderCarta } from '../services/api';

const TIPOS = [
  { id: 'bronce', nombre: 'Sobre bronce', coste: 750 },
  { id: 'plata', nombre: 'Sobre plata', coste: 2500 },
  { id: 'oro', nombre: 'Sobre oro', coste: 7500 },
  { id: 'oro-premium', nombre: 'Sobre oro premium', coste: 15000 },
  { id: 'especial', nombre: 'Sobre especial', coste: 25000 },
];

function Sobres() {
  const [monedas, setMonedas] = useState(0);
  const [resultado, setResultado] = useState(null);
  const [opening, setOpening] = useState(false);
  const [step, setStep] = useState(0);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getMonedero().then(res => setMonedas(res.data.monedas)).catch(() => setError('No se pudo cargar el monedero'));
  }, []);

  const handleAbrir = async (tipo) => {
    setLoading(true);
    setOpening(true);
    setStep(0);
    setError('');
    try {
      for (let i = 1; i <= 5; i++) {
        await new Promise(resolve => setTimeout(resolve, 650));
        setStep(i);
      }
      const res = await abrirSobre(tipo);
      setResultado(res.data);
      setMonedas(res.data.monedasRestantes);
      setStep(6);
    } catch (err) {
      setError(err.response?.data || 'No se pudo abrir el sobre');
      setOpening(false);
    } finally {
      setLoading(false);
    }
  };

  const cartaPrincipal = resultado?.cartas?.slice().sort((a, b) => (b.media || 0) - (a.media || 0))[0];
  const esEspecial = cartaPrincipal && ((cartaPrincipal.media || 0) >= 85 || String(cartaPrincipal.rareza || '').includes('Especial'));

  const venderRapido = async () => {
    if (!resultado) return;
    try {
      let ultimasMonedas = monedas;
      for (const carta of resultado.cartas) {
        const res = await venderCarta(carta.id);
        ultimasMonedas = res.data.monedas;
      }
      setMonedas(ultimasMonedas);
      setOpening(false);
      setResultado(null);
    } catch {
      setError('No se pudieron vender las cartas');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h2>Sobres</h2>
        <div className="coin-pill">{monedas} monedas</div>
      </div>

      {error && <div className="alert alert-error">{error}</div>}

      {opening && (
        <div className="pack-opening-screen">
          <div className={esEspecial ? 'special-confetti' : ''} />
          <div className="pack-light-left" />
          <div className="pack-light-right" />
          {step < 6 || !cartaPrincipal ? (
            <div className="pack-stage">
              <div className="pack-glow" />
              <div className="closed-pack">FUT</div>
              <div className="pack-reveal-step">
                {step === 0 && 'Preparando sobre'}
                {step === 1 && 'Rareza'}
                {step === 2 && 'Nacionalidad'}
                {step === 3 && 'Posicion'}
                {step === 4 && 'Club'}
                {step === 5 && 'Jugador'}
              </div>
            </div>
          ) : (
            <div className="card-final-reveal">
              {esEspecial && <div className="special-text">Carta especial</div>}
              <div className="fut-card especial">
                <div className="rating">{cartaPrincipal.media}</div>
                <div className="pos-badge">{cartaPrincipal.posicion}</div>
                {cartaPrincipal.imagenJugador && <img className="player-img" src={cartaPrincipal.imagenJugador} alt={cartaPrincipal.nombreJugador || cartaPrincipal.jugadorNombre} />}
                <div className="jugador-name">{cartaPrincipal.nombreJugador || cartaPrincipal.jugadorNombre || cartaPrincipal.club}</div>
                <div className="equipo-name">{cartaPrincipal.nacionalidad}</div>
                <div className="equipo-name">{cartaPrincipal.club}</div>
                <div className="equipo-name">{cartaPrincipal.rareza}</div>
              </div>
              <div className="form-actions reveal-actions">
                <button className="btn btn-primary" onClick={() => setOpening(false)}>Enviar al club</button>
                <button className="btn btn-secondary" onClick={venderRapido}>Vender rapido</button>
              </div>
            </div>
          )}
        </div>
      )}

      <div className="pack-grid">
        {TIPOS.map(tipo => (
          <div key={tipo.id} className={`pack-card pack-${tipo.id}`}>
            <h3>{tipo.nombre}</h3>
            <p>{tipo.coste} monedas</p>
            <button className="btn btn-primary" disabled={loading} onClick={() => handleAbrir(tipo.id)}>
              Abrir
            </button>
          </div>
        ))}
      </div>

      {resultado && (
        <>
          <div className="page-header" style={{ marginTop: '2rem' }}>
            <h2>Cartas obtenidas</h2>
          </div>
          <div className="jugadores-grid">
            {resultado.cartas.map(carta => (
              <div key={carta.id} className="fut-card oro">
                <div className="rating">{carta.media}</div>
                <div className="pos-badge">{carta.posicion}</div>
                {carta.imagenJugador && <img className="player-img" src={carta.imagenJugador} alt={carta.nombreJugador || carta.jugadorNombre} />}
                <div className="jugador-name">{carta.nombreJugador || carta.jugadorNombre || carta.club}</div>
                <div className="equipo-name">{carta.rareza}</div>
                <div className="equipo-name">{carta.club}</div>
              </div>
            ))}
          </div>
        </>
      )}
    </div>
  );
}

export default Sobres;
