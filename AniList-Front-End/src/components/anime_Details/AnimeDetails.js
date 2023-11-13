import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './AnimeDetails.css'; // Importa el archivo CSS

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
            bannerImage 
            trailer {
              id
              site
              thumbnail
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
        if (animeData.bannerImage === null) {
          const randomBanner ="https://i.pinimg.com/736x/86/e3/34/86e3344c625be5db137c16d6d06e5ad3.jpg";
      animeData.bannerImage = randomBanner;

        }
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
    <div className="ban">
    {animeDetails && animeDetails.coverImage && (
      <div className="content-wrapper">
        <img src={animeDetails.bannerImage} alt="BannerImage" className="banner" />
        <div className="anime-details">
          <div className="cover-wrap-inner">
            <div className="anime-botton">
              <div className="anime-botton2">
                <img src={animeDetails.coverImage.large} alt={animeDetails.title.romaji} className='ImagenAnime' />
                <button className="button" onClick={() => alert("Reproducir")}>ADD TO ANIME </button>
              </div>
            </div>
            <div className="anime-info">
              <h2>{animeDetails.title.romaji}</h2>
              <p>Descripción: {animeDetails.description}</p>
              {animeDetails.trailer && (
                <div className="trailer">
                  <p>Tráiler:</p>
                  <iframe
                    title="Tráiler"
                    width="900"
                    height="450"
                    src={`https://www.youtube.com/embed/${animeDetails.trailer.id}`}
                    allowFullScreen
                    style={{
                      border: 'none',
                      margin: '0 auto', // Centra el iframe horizontalmente
                      display: 'block', // Elimina el espacio extra debajo del iframe
                    }}
                  />
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
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
