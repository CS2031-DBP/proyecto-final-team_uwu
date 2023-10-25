import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Importa Link de React Router
import axios from 'axios';
import { Respuestas } from './Respuestas/Respuestas';
import { Responder } from './Respuestas/Responder';

const Thread = ({userId}) => {
    const { id } = useParams();

    const [hilos, setHilos] = useState({ tema: '', contenido: '', fechaCreacion: '', respuestaIds: [] });
    useEffect(() => {

    console.log(id);
    // Realiza una solicitud GET para obtener la lista de hilos desde tu backend
    axios.get('http://localhost:8080/api/auth/hilos/'+id)
      .then((response) => {
        setHilos(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error('Error al obtener los hilos:', error);
      });
  }, [id]);
  const handleRespuestaSubmit = (hiloId,respuesta) => {
    // Enviar la respuesta al backend
    axios.post(`http://localhost:8080/api/auth/respuestas/${userId}/${hiloId}`, { contenido: respuesta })
      .then((response) => {
        // Actualizar la vista o realizar cualquier acción necesaria después de enviar la respuesta
        console.log('Respuesta enviada con éxito:', response.data);
        window.location.reload();

      })
      .catch((error) => {
        console.error('Error al enviar la respuesta:', error);
      });
  };
  return (
    <div>
        {hilos.tema}
        <div>{hilos.contenido}</div>
        <div>{hilos.fechaCreacion}</div>
        {hilos.respuestaIds.map((respuestaId) => (
        <Respuestas key={respuestaId} respuestaId={respuestaId} />
      ))}
        {userId && (
        <Responder hiloId={id} onRespuestaSubmit={handleRespuestaSubmit} />

        )}

    </div>
  );
};

export default Thread;
