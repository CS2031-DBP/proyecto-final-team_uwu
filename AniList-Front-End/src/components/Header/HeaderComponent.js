import React, { useState } from 'react'

import '../Header/styles/header.css'
import { SearchBar } from '../barraBusqueda/SearchBar';
import { SearchResults } from '../barraBusqueda/SearchResults';
import { Link } from 'react-router-dom';
import { useAuth } from '../Autenticacion/AuthContext'; // Importa el contexto

export const HeaderComponent = () => {
    const [results, setResults] = useState([]);
    const [clearSearch, setClearSearch] = useState([]);
    const { state, dispatch } = useAuth(); // Obtiene el estado y la función de despacho del contexto
    const handleLogout = () => {
        dispatch({ type: 'LOGOUT' });
      };
    console.log(state.isAuthenticated);
  return (
    <div className='encabezado hider' > 
        <div className='wrap'>
            <a className='logo' href='/'>
            <img src={require('../images/emotico.png')} alt="Portada del anime" />
            </a>
            <h1 className="nav__title">W/A</h1>
            <div className='links'>
                <a href='/' className='link'>Home</a>
                <a href='/' className='link'>Anime List</a>
                <a href='/Threads' className='link'>Forum</a>

            </div>
                <div className='search-bar-container'>
                    <SearchBar setResults={setResults} clearSearch={setClearSearch} />
                    <SearchResults results={results} clearSearch = {clearSearch} />
                </div>

                {state.isAuthenticated ? (
        <button onClick={handleLogout} className='button_header'>Cerrar Sesión</button>
      ) : (
        <div className='links_login'>
          <Link to="/Login" className="link">
            Login
          </Link>
          <Link to="/Register" className="link">
            Register
          </Link>
        </div>
        
      )}  
            </div>
      
    </div> )
}
export default HeaderComponent;
