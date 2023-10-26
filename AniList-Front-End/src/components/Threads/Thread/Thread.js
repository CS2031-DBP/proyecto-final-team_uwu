import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Importa Link de React Router
import axios from 'axios';
import { Respuestas } from './Respuestas/Respuestas';
import { Responder } from './Respuestas/Responder';

const Thread = ({userId}) => {
    const { id } = useParams();

    const [hilos, setHilos] = useState({ tema: '', contenido: '', fechaCreacion: '',userNickname: '', respuestaIds: [] });
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

  const calculateTimeAgo = (createdDate) => {
    const currentDate = new Date();
    const createdDateObj = new Date(createdDate);
    const timeDifference = currentDate - createdDateObj;
    const seconds = Math.floor(timeDifference / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const years = Math.floor(days / 365);

    if (years > 0) {
      return `replied ${years} ${years === 1 ? 'year' : 'years'} ago`;
    } else if (days > 0) {
      return `replied ${days} ${days === 1 ? 'day' : 'days'} ago`;
    } else if (hours > 0) {
      return `replied ${hours} ${hours === 1 ? 'hour' : 'hours'} ago`;
    } else if (minutes > 0) {
      return `replied ${minutes} ${minutes === 1 ? 'minute' : 'minutes'} ago`;
    } else {
      return `replied ${seconds} ${seconds === 1 ? 'second' : 'seconds'} ago`;
    }
  };
  return (
    <div className='Page_content_forum'>
      <div className='forum_container'>
        <h1 className='title'>{hilos.tema}</h1>
        <div className='body'>
          <div className='header'>
            <div className='user'>{hilos.userNickname}</div>
            <div className='info'>
              <div className='time'>{calculateTimeAgo(hilos.fechaCreacion)}</div>
            </div>
          </div>
          <div className='markdown'>
            <p>{hilos.contenido}</p>
          </div>


        </div>
        <div className='comments'>
        {hilos.respuestaIds.map((respuestaId) => (
          <Respuestas key={respuestaId} respuestaId={respuestaId} />
          ))}
          {userId && (
          <Responder hiloId={id} onRespuestaSubmit={handleRespuestaSubmit} />
          )}
        </div>
      </div>


    </div>
  );
};

export default Thread;
