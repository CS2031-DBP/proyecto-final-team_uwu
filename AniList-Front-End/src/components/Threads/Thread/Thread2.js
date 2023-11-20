import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'; // Importa Link de React Router
import ListComment from './Respuestas/ListComment';

export const Thread2 = () => {
    const { id } = useParams();
    const [listcomments, setListcomments] = useState([]);
    console.log(id);
    useEffect(() => {
        axios.get('http://localhost:8080/respuestas/rootMessage/'+id)
          .then((response) => {
            setListcomments(response.data);
          })
          .catch((error) => {
            console.error('Error al obtener los hilos:', error);
          });
    }, [id]);

  return (
    <div className='Page_content_forum'>
        <ListComment listcomments = {listcomments}/>
    </div>
  )
}
