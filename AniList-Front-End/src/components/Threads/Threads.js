import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom'; // Importa Link de React Router
import axios from 'axios';

const Threads = () => {
  const [hilos, setHilos] = useState([]);

  useEffect(() => {
    // Realiza una solicitud GET para obtener la lista de hilos desde tu backend
    axios.get('http://localhost:8080/hilos/allHilos')
      .then((response) => {
        setHilos(response.data);
      })
      .catch((error) => {
        console.error('Error al obtener los hilos:', error);
      });
  }, []);

  return (
    <div>
      <h1>Lista de Hilos</h1>
        {hilos.map((hilo) => (
          <li key={hilo.id}>
            <div>
              <strong>Tema:</strong> 
              {/* Utiliza un enlace para redireccionar al usuario a la ruta individual del hilo */}
              <Link to={`/Threads/${hilo.id}`}>{hilo.tema}</Link>
            </div>
            <div>
              <strong>Usuario:</strong> {hilo.userNickname}
            </div>
          </li>
        ))}
    </div>
  );
};

export default Threads;
