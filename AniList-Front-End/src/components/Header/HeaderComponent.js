import React, { useState } from 'react'

import '../Header/styles/header.css'
import { SearchBar } from '../barraBusqueda/SearchBar';
import { SearchResults } from '../barraBusqueda/SearchResults';

export const HeaderComponent = () => {
  const [results, setResults] = useState([]);
  const [clearSearch, setClearSearch] = useState([]);

  return (
    <div className='encabezado hider' > 
        <div className='wrap'>
            <a className='logo' href='/'>
            <img src={require('../images/emotico.png')} alt="Portada del anime" />
            </a>
            <h1 class="nav__title">W/A</h1>
            <div className='links'>
                <a href='/' className='link'>Home</a>
                <a href='/' className='link'>Anime List</a>
                <a href='/' className='link'>Forum</a>
            </div>
                <div className='search-bar-container'>
                    <SearchBar setResults={setResults} clearSearch={setClearSearch} />
                    <SearchResults results={results} clearSearch = {clearSearch} />
                </div>  
            </div>
    </div> )
}
export default HeaderComponent;
