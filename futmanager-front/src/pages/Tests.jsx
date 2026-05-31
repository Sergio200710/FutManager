import { useState, useEffect } from 'react';
import { getTestResults } from '../services/api';

function Tests() {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  const fetchTests = async () => {
    setLoading(true);
    setError('');
    try {
      const res = await getTestResults();
      setData(res.data);
    } catch {
      setError('No se pudo conectar con el backend. ¿Está arrancado Spring Boot?');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchTests(); }, []);

  return (
    <div>
      <div className="page-header">
        <h2>Panel de Tests</h2>
        <button className="btn btn-secondary" onClick={fetchTests} disabled={loading}>
          🔄 Recargar
        </button>
      </div>

      {loading && (
        <div className="loading">
          <div className="loading-spinner" />
          Ejecutando tests...
        </div>
      )}

      {error && !loading && (
        <div className="alert alert-error">{error}</div>
      )}

      {data && !loading && (
        <>
          {/* Resumen */}
          <div className="test-summary">
            <div className="test-stat total">
              <div className="num">{data.totalTests}</div>
              <div className="lbl">Total</div>
            </div>
            <div className="test-stat passed">
              <div className="num">{data.passed}</div>
              <div className="lbl">Pasados</div>
            </div>
            <div className="test-stat failed">
              <div className="num">{data.failed}</div>
              <div className="lbl">Fallidos</div>
            </div>
            <div className="test-stat skipped">
              <div className="num">{data.skipped}</div>
              <div className="lbl">Saltados</div>
            </div>
          </div>

          {/* Barra de progreso */}
          {data.totalTests > 0 && (
            <div style={{ marginBottom: '2rem' }}>
              <div style={{
                height: '8px', borderRadius: '4px',
                background: 'rgba(255,255,255,0.08)',
                overflow: 'hidden'
              }}>
                <div style={{
                  height: '100%',
                  width: `${(data.passed / data.totalTests) * 100}%`,
                  background: 'linear-gradient(90deg, var(--green), #00a854)',
                  borderRadius: '4px',
                  transition: 'width 0.5s ease'
                }} />
              </div>
              <p style={{ color: 'var(--text-muted)', fontSize: '0.8rem', marginTop: '0.4rem' }}>
                {Math.round((data.passed / data.totalTests) * 100)}% de tests pasados
              </p>
            </div>
          )}

          {/* Lista de tests */}
          {data.tests && data.tests.length > 0 && (
            <div className="card">
              <table className="data-table">
                <thead>
                  <tr>
                    <th>Clase</th>
                    <th>Test</th>
                    <th>Estado</th>
                    <th>Mensaje</th>
                  </tr>
                </thead>
                <tbody>
                  {data.tests.map((t, i) => (
                    <tr key={i}>
                      <td style={{ color: 'var(--text-muted)', fontSize: '0.8rem' }}>{t.className}</td>
                      <td style={{ fontWeight: 500 }}>{t.testName}</td>
                      <td>
                        <span className={`badge badge-${t.status.toLowerCase()}`}>
                          {t.status === 'PASSED' ? '✅ PASADO' : t.status === 'FAILED' ? '❌ FALLIDO' : '⏭️ SALTADO'}
                        </span>
                      </td>
                      <td style={{ color: 'var(--text-muted)', fontSize: '0.82rem' }}>
                        {t.message || '—'}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}

          {(!data.tests || data.tests.length === 0) && (
            <div className="card" style={{ textAlign: 'center', color: 'var(--text-muted)', padding: '2rem' }}>
              ✅ Todos los tests pasaron sin fallos registrados
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default Tests;
