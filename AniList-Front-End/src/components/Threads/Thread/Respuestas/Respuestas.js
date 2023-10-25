import axios from 'axios';
import React, { useEffect, useState } from 'react'

export const Respuestas = ({respuestaId }) => {

    const [message, setMessage] = useState({ contenido: '',usuarioM: {} });
    useEffect(() => {

        // Realiza una solicitud GET para obtener la lista de hilos desde tu backend
        axios.get('http://localhost:8080/api/auth/respuestas/'+ respuestaId)
          .then((response) => {
            setMessage(response.data);
            console.log(response.data);
          })
          .catch((error) => {
            console.error('Error al obtener los hilos:', error);
          });
      }, [respuestaId]);
  return (
    <div>
      <p>Respuesta {message.contenido}</p>
      <p>Usuario {message.usuarioM.nickname}</p>
    </div>  
    )
}
