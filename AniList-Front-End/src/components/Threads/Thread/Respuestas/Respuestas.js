import axios from 'axios';
import React, { useEffect, useState } from 'react'

export const Respuestas = ({respuestaId }) => {

    const [message, setMessage] = useState({ contenido: '',subRespuestaIds: [] });
    useEffect(() => {

        // Realiza una solicitud GET para obtener la lista de hilos desde tu backend
        axios.get('http://localhost:8080/respuestas/'+ respuestaId)
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
    </div>  
    )
}
