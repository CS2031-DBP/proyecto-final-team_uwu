import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

export const AnimeDetails = () => {
  const { id } = useParams();
  const [animeDetails, setAnimeDetails] = useState(null);

  useEffect(() => {
    const fetchAnimeDetails = async () => {
      const query = `
        query ($id: Int) {
          Media (id: $id, type: ANIME) {
            id
            title {
              romaji
            }
            description
            coverImage {
              large
            }
          }
        }
      `;

      const variables = {
        id: parseInt(id),
      };

      try {
        const response = await axios.post('https://graphql.anilist.co', {
          query: query,
          variables: variables,
        });

        const animeData = response.data.data.Media;
        if (animeData.description !== null){
          animeData.description = cleanDescription(animeData.description);
        }
     
        setAnimeDetails(animeData);

      } catch (error) {
        console.error('Error fetching anime details:', error);
      }
    };

    // Llama a la función para obtener los detalles del anime cuando el componente se monta
    fetchAnimeDetails();

  }, [id]);
  return (
    <div>
      <h1>Detalles del Anime</h1>
      {animeDetails ? (
        <>
          <h2>Nombre del Anime (romaji): {animeDetails.title.romaji}</h2>
          <p>Descripción: {animeDetails.description}</p>
          <img src={animeDetails.coverImage.large} alt={animeDetails.title.romaji} />
        </>
      ) : (
        <div>Cargando...</div>
      )}
    </div>
  );
};
// Funcion para eliminar etiquetas de HTML
function cleanDescription(description) {
  // Eliminar etiquetas HTML
  const cleanText = description.replace(/<[^>]+>/g, '');

  // Eliminar espacios en blanco duplicados y otros caracteres no deseados
  const finalCleanText = cleanText.replace(/\s+/g, ' ').trim();

  return finalCleanText;
}

export default AnimeDetails;
