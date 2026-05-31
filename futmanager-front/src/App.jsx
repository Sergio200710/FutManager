import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import Home from './pages/Home';
import Equipos from './pages/Equipos';
import Jugadores from './pages/Jugadores';
import Cartas from './pages/Cartas';
import Plantillas from './pages/Plantillas';
import Simulador from './pages/Simulador';
import Sobres from './pages/Sobres';
import Mercado from './pages/Mercado';
import MisCartas from './pages/MisCartas';
import Objetivos from './pages/Objetivos';
import Evoluciones from './pages/Evoluciones';
import Tests from './pages/Tests';
import './index.css';

function App() {
  return (
    <Router>
      <nav className="navbar">
        <ul>
          <li><span className="brand">⚽ FutManager</span></li>
          <li><NavLink to="/">Inicio</NavLink></li>
          <li><NavLink to="/jugadores">Jugadores</NavLink></li>
          <li><NavLink to="/equipos">Equipos</NavLink></li>
          <li><NavLink to="/cartas">Cartas FUT</NavLink></li>
          <li><NavLink to="/plantillas">Plantillas</NavLink></li>
          <li><NavLink to="/simulador">Simulador</NavLink></li>
          <li><NavLink to="/sobres">Sobres</NavLink></li>
          <li><NavLink to="/mercado">Mercado</NavLink></li>
          <li><NavLink to="/mis-cartas">Mis cartas</NavLink></li>
          <li><NavLink to="/objetivos">Objetivos</NavLink></li>
          <li><NavLink to="/evoluciones">Evoluciones</NavLink></li>
          <li><NavLink to="/tests">Tests</NavLink></li>
        </ul>
      </nav>
      <div className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/jugadores" element={<Jugadores />} />
          <Route path="/equipos" element={<Equipos />} />
          <Route path="/cartas" element={<Cartas />} />
          <Route path="/plantillas" element={<Plantillas />} />
          <Route path="/simulador" element={<Simulador />} />
          <Route path="/sobres" element={<Sobres />} />
          <Route path="/mercado" element={<Mercado />} />
          <Route path="/mis-cartas" element={<MisCartas />} />
          <Route path="/objetivos" element={<Objetivos />} />
          <Route path="/evoluciones" element={<Evoluciones />} />
          <Route path="/tests" element={<Tests />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
