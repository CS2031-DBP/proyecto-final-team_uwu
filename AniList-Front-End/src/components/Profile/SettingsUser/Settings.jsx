import React, { useEffect, useState } from 'react'
import { Route, Routes } from 'react-router-dom';
import { Profile } from './routesSettings/Profile';
import { Account } from './routesSettings/Account';
import './styles/settings.css'

export const Settings = ({userId}) => {
    const [selectedSettingItem, setSelectedSettingItem] = useState('profile');
    
    useEffect(() => {
        // Recuperar el estado del elemento de navegación desde localStorage
        const storedNavItem = localStorage.getItem('selectedNavItem');
        if (storedNavItem) {
            setSelectedSettingItem(storedNavItem);
        }
      }, []); // Se ejecuta solo una vez al montar el componente
    
      // DESMONTAR
        useEffect(() => {
            // Limpiar el valor almacenado en localStorage cuando el componente se desmonta
            return () => {
            localStorage.removeItem('selectedNavItem');
            };
        }, []);
    const handleNavItemClick = (navItem) => {
        setSelectedSettingItem(navItem);
        // Almacenar el estado del elemento de navegación en localStorage
        localStorage.setItem('selectedNavItem', navItem);
    };
  return (
    <div className='page_content'>
        <div className='settings container'>
            <div className='nav_options'>
                <div className='nav_group_header'>Settings</div>
                <a 
                    href={`/settings/`}
                    className={`link ${selectedSettingItem === 'profile' ? 'active' : ''}`}
                    onClick={() => handleNavItemClick('profile')}
                >
                Profile
                </a>
                <a 
                    href={`/settings/account`}
                    className={`link ${selectedSettingItem === 'account' ? 'active' : ''}`}
                    onClick={() => handleNavItemClick('account')}
                >
                Account
                </a>
            </div>
            <div className='content_nav'>
            <Routes>
                <Route path="/" element={<Profile userId={userId}/>} />
                <Route path="/account" element={<Account userId={userId}/>} />
            </Routes>
            </div>
        </div>

    </div>
  )
}
