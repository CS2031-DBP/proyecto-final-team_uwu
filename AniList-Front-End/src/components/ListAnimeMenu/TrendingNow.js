import axios from 'axios';
import React, { useEffect, useState } from 'react';

export const TrendingNow = () => {
  const [trendingAnimeData, setTrendingAnimeData] = useState([]);
  
  // Todo lo pongo dentro de un useEffect para evitar warning de cambios
  
  useEffect(() => {
    const apiUrl = 'https://graphql.anilist.co';
    const queryText = `
      query {
        Page {
          media(season: FALL, seasonYear: 2023, type: ANIME, sort: TRENDING_DESC, isAdult: false) {
            id
            title {
              romaji
            }
            coverImage {
              large
            }
            averageScore
            genres
          }
        }
      }
    `;
    
    const variables = {};
    const fetchData = async () => {
      try {
        const response = await axios.post(apiUrl, {
          query: queryText,
          variables: variables,
        });
    
        console.log("resultados");
        console.log(response.data.data.Page.media); // Cambié `response.data.data.Page.media` a `response.data` ya que la respuesta completa se encuentra en `response.data`.
        setTrendingAnimeData(response.data.data.Page.media);
      } catch (error) {
        console.error('Error fetching anime data:', error);
      }
    }
      fetchData();
  }, []);
  return (
    <div id='trending-anime-list' className='ActivityAnimeList'>
      {trendingAnimeData.map((anime) =>(
        <div className='AnimeActivity'>
          <div className='hover-data-right'>
          {anime.title.romaji}
          <div className='score'>
            <img className='iconImage' alt='' src={anime.averageScore !== null?
               (anime.averageScore > 75 ? 'images/score/smile-regular-24.png' : 
               (anime.averageScore > 60 && anime.averageScore < 76 ? 'images/score/meh-regular-24.png' : 
                 'images/score/sad-regular-24.png')
               ) : ''}>
            </img>
            <div className='percentage'>
              {anime.averageScore + '%'}
            </div>
          </div>
          <div className="genres">
              {anime.genres.map(genero => (
                <div className="genere">
                  {genero}
                </div>
              ))}
            </div>
          </div>
          <a className='cover2' href={'anime/'+anime.id}>
            <img className='anime-image2' alt='' src={anime.coverImage.large}></img>
          </a>
          <h2 className='anime-title2'>{anime.title.romaji}</h2>
        </div>




      ))}
      
      </div>
  );
};

