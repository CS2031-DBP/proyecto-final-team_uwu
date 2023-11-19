import React from 'react'
import { SearchBarResultados } from './barraBusqueda/SearchBarResultados'
import { TrendingNow } from './ListAnimeMenu/TrendingNow'
import { Videos } from './Videos/Videos'

import '../components/ListAnimeMenu/styles/SectionAnime.css'
export const Index = () => {
      // Verifica si el usuario está autenticado (por ejemplo, si existe un token en localStorage)
  // SearchBarResultados -> PORTADA
// Verifica si el usuario está autenticado (por ejemplo, si existe un token en localStorage)
  // SearchBarResultados -> PORTADA
  return (
    <div className='Container'>
      <SearchBarResultados/> 
      <div className='ListAnime_Section'>
        <div className='title'>
          <h2>Trending Now</h2>
        </div>
        <TrendingNow/>
        <div className='title'>
          <h3>Anime Videos</h3>
          <Videos/>

        </div>
      

      </div>
    </div>
  );
}

