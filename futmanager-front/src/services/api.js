import axios from 'axios';

const API_URL = 'http://localhost:8080';

export const api = axios.create({
    baseURL: API_URL,
});

// Equipos
export const getEquipos = () => api.get('/api/equipos');
export const getEquipo = (id) => api.get(`/api/equipos/${id}`);
export const createEquipo = (equipo) => api.post('/api/equipos', equipo);
export const updateEquipo = (id, equipo) => api.put(`/api/equipos/${id}`, equipo);
export const deleteEquipo = (id) => api.delete(`/api/equipos/${id}`);

// Jugadores
export const getJugadores = (params) => api.get('/api/jugadores', { params });
export const getJugador = (id) => api.get(`/api/jugadores/${id}`);
export const createJugador = (jugador) => api.post('/api/jugadores', jugador);
export const updateJugador = (id, jugador) => api.put(`/api/jugadores/${id}`, jugador);
export const deleteJugador = (id) => api.delete(`/api/jugadores/${id}`);

// Cartas FUT
export const getCartas = (params) => api.get('/api/cartas', { params });
export const buscarCartas = (params) => api.get('/api/cartas/buscar', { params });
export const getCarta = (id) => api.get(`/api/cartas/${id}`);
export const getMisCartas = () => api.get('/api/cartas/mis-cartas');
export const getCartasClub = () => api.get('/api/club/cartas');
export const venderCartaClub = (cartaId) => api.post(`/api/club/cartas/${cartaId}/vender`);
export const cambiarTransferible = (cartaId, transferible) => api.put(`/api/club/cartas/${cartaId}/transferible`, null, { params: { transferible } });
export const createCarta = (carta) => api.post('/api/cartas', carta);
export const updateCarta = (id, carta) => api.put(`/api/cartas/${id}`, carta);
export const deleteCarta = (id) => api.delete(`/api/cartas/${id}`);
export const importarCartasApi = () => api.get('/api/cartas/importar-api');
export const importarTodo = () => api.get('/api/importar/todo');
export const importarTodoReal = () => api.get('/api/importar/todo-real');

// Plantillas
export const getPlantillas = () => api.get('/api/plantillas');
export const getPlantilla = (id) => api.get(`/api/plantillas/${id}`);
export const createPlantilla = (plantilla) => api.post('/api/plantillas', plantilla);
export const updatePlantilla = (id, plantilla) => api.put(`/api/plantillas/${id}`, plantilla);
export const deletePlantilla = (id) => api.delete(`/api/plantillas/${id}`);
export const addCartaPlantilla = (plantillaId, cartaId) => api.post(`/api/plantillas/${plantillaId}/cartas/${cartaId}`);
export const removeCartaPlantilla = (plantillaId, cartaId) => api.delete(`/api/plantillas/${plantillaId}/cartas/${cartaId}`);

// Simulador
export const simularPartido = (plantillaId) => api.post(`/api/simulador/plantilla/${plantillaId}`);
export const getPartidos = () => api.get('/api/partidos');
export const getPartido = (id) => api.get(`/api/partidos/${id}`);

// Monedas, sobres y mercado
export const getMonedero = () => api.get('/api/monedero');
export const getClub = () => api.get('/api/club');
export const updateClub = (club) => api.put('/api/club', club);
export const abrirSobre = (tipoSobre) => api.post(`/api/sobres/abrir/${tipoSobre}`);
export const getSobres = () => api.get('/api/sobres');
export const getMercadoCartas = (params) => api.get('/api/mercado/cartas', { params });
export const comprarCarta = (cartaId) => api.post(`/api/mercado/comprar/${cartaId}`);
export const venderCarta = (cartaId) => api.post(`/api/mercado/vender/${cartaId}`);
export const getObjetivos = () => api.get('/api/objetivos');
export const completarObjetivo = (id) => api.post(`/api/objetivos/${id}/completar`);
export const getEvoluciones = () => api.get('/api/evoluciones');
export const evolucionarCarta = (cartaId) => api.post(`/api/evoluciones/carta/${cartaId}`);

// Tests
export const getTestResults = () => api.get('/api/test-results');
