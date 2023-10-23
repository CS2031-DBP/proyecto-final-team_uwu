import React from 'react'
import { SearchBarResultados } from './barraBusqueda/SearchBarResultados'
import { TrendingNow } from './ListAnimeMenu/TrendingNow'
import '../components/ListAnimeMenu/styles/SectionAnime.css'
export const Index = () => {
  // SearchBarResultados -> PORTADA
  return (
    <div className='Container'>
        <SearchBarResultados/> 
        <div className='ListAnime_Section'>
        <div className='title'>
            <h2>Trending Now</h2>
        </div>
        <TrendingNow/>
        </div>
    </div>
  )
}

