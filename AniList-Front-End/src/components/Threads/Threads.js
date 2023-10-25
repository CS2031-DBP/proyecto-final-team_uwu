import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Threads = ({ userId }) => {
  const [hilos, setHilos] = useState([]);
  const [nuevoHilo, setNuevoHilo] = useState({ tema: '', contenido: '' });

  useEffect(() => {
    axios.get('http://localhost:8080/api/auth/hilos')
      .then((response) => {
        setHilos(response.data);
      })
      .catch((error) => {
        console.error('Error al obtener los hilos:', error);
      });
  }, []);

  const handleCreateHilo = () => {
    // Realizar una solicitud POST al backend para crear un nuevo hilo
    axios.post(`http://localhost:8080/api/auth/hilos/${userId}`, nuevoHilo)
      .then((response) => {
        // Agregar el nuevo hilo a la lista de hilos
        setHilos([...hilos, response.data]);

        // Limpiar el formulario
        setNuevoHilo({ tema: '', contenido: '' });
      })
      .catch((error) => {
        console.error('Error al crear el hilo:', error);
      });
  };

  return (
    <div className='Page_content_forum'>
      <div className='forum_container'>
        <div className='feed'>
          <div>
            {hilos.map((hilo) => (
              <li key={hilo.id}>
                <div className='Thread'>
                  <strong>Tema:</strong>
                  <Link to={`/Threads/${hilo.id}`}>{hilo.tema}</Link>
                </div>
                <div>
                  <strong>Usuario:</strong> {hilo.userNickname}
                </div>
              </li>
            ))}
          </div>
        </div>
      </div>
      {userId && (
        // Formulario para crear un nuevo hilo
        <div>
          <h2>Crear Nuevo Hilo</h2>
          <div>
            <input
              type='text'
              placeholder='Tema'
              value={nuevoHilo.tema}
              onChange={(e) => setNuevoHilo({ ...nuevoHilo, tema: e.target.value })}
            />
          </div>
          <div>
            <textarea
              placeholder='Contenido'
              value={nuevoHilo.contenido}
              onChange={(e) => setNuevoHilo({ ...nuevoHilo, contenido: e.target.value })}
            />
          </div>
          <button onClick={handleCreateHilo}>Guardar</button>
        </div>
      )}
    </div>
  );
};

export default Threads;
