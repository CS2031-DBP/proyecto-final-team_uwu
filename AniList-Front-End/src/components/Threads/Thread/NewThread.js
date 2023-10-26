// NewThread.js
import React, { useState } from 'react';
import axios from 'axios';

const NewThread = ({ userId }) => {
  const [nuevoHilo, setNuevoHilo] = useState({ tema: '', contenido: '' });

  const handleCreateHilo = () => {
    // Realizar una solicitud POST al backend para crear un nuevo hilo
    axios.post(`http://localhost:8080/api/auth/hilos/${userId}`, nuevoHilo)
      .then((response) => {
        // Redirigir al usuario a la página de hilos después de crear el hilo
        window.location.href = '/Threads/'+response.data.id;
      })
      .catch((error) => {
        console.error('Error al crear el hilo:', error);
      });
  };

  return (
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
      <button onClick={handleCreateHilo}>Crear Hilo</button>
    </div>
  );
};

export default NewThread;
