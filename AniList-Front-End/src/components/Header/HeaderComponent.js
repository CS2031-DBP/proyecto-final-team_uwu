import React, { useState } from 'react';
import '../Header/styles/header.css';
import { SearchBar } from '../barraBusqueda/SearchBar';
import { SearchResults } from '../barraBusqueda/SearchResults';

export const HeaderComponent = ({ userId ,idName}) => {
  const [results, setResults] = useState([]);
  const [clearSearch, setClearSearch] = useState([]);
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const handleLogout = () => {
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    window.location.reload();
  };

  const toogleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <div className='encabezado'>
      <div className='wrap'>
        <a className='logo' href='/'>
          <img src={require('../images/emotico.png')} alt="Portada del anime" />
        </a>
        <h1 className="nav__title">A/F</h1>
        <div className='links'>
          <a href='/' className='link'>Home</a>
          <a href='/' className='link'>Anime List</a>
          <a href='/Threads' className='link'>Forum</a>
        </div>
        <div className='search-bar-container'>
          <SearchBar setResults={setResults} clearSearch={setClearSearch} />
          <SearchResults results={results} clearSearch={clearSearch} />
        </div>
        {userId ? (
          <div className='profile'>
            <img src="images/profile/profile.png" alt="profile" onClick={toogleMenu} />
            <div className={`sub_menu_wrap ${isMenuOpen ? 'active' : ''}`} id='subMenu'>
                <div className='sub_menu'>
                  <div className='user_info'>
                    <h2>{idName}</h2>
                  </div>
                  <hr/>
                  <a href="/" className='sub_menu_link'>
                    <ion-icon name="person-circle-outline"></ion-icon>
                    <p>Edit Profile</p>
                    <span> > </span>
                  </a>
                  <a href="/" className='sub_menu_link'>
                    <ion-icon name="settings-outline"></ion-icon>
                    <p>Settings & Privacy</p>
                    <span> > </span>
                  </a>
                  <div className='sub_menu_link' onClick={handleLogout}>
                    <ion-icon name="log-in-outline"></ion-icon>
                    <p>Logout</p>
                    <span> > </span>
                  </div>
                </div>
              </div>
          </div>
        ) : (
          <div className='profile'>
            <img src="images/profile/profile.png" alt="profile" onClick={toogleMenu} />
            <div className={`sub_menu_wrap ${isMenuOpen ? 'active' : ''}`} id='subMenu'>
                <div className='sub_menu'>
                  <a href="/Login" className='sub_menu_link'>
                  <ion-icon name="person-outline"></ion-icon>    
                    <p>Login</p>
                    <span> > </span>
                  </a>
                  <a href="/Register" className='sub_menu_link'>
                  <ion-icon name="person-add-outline"></ion-icon>     
                    <p>Register</p>
                    <span> > </span>
                  </a>
                </div>
              </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default HeaderComponent;
