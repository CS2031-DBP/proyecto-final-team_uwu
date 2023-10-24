import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../Autenticacion/AuthContext'; // Importa el contexto

import './styles/Login.css';

export const Login = () => {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });

  const navigate = useNavigate();
  const { dispatch } = useAuth();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('http://localhost:8080/api/auth/signin', formData);

      if (response.status === 200) {
        // Si la respuesta es exitosa (código 200), actualiza el estado de autenticación
        dispatch({ type: 'LOGIN', token: response.token });
        navigate('/'); // Reemplaza '/pagina-principal' con la URL correcta
      } else {
        // Manejar otros casos si es necesario
      }
    } catch (error) {
      // Manejar errores, por ejemplo, mostrar un mensaje de usuario no registrado
      console.error('Error al iniciar sesión:', error);
    }
  };

  return (
    <div className='Container_Login'>
      <div className="Section_login">
        <div className='login_header'>
          <h2> Sign In</h2>
        </div>
        <form onSubmit={handleSubmit}>
          <div className='inputBox'>
            <input
              type='text'
              name='email'
              placeholder='Username'
              value={formData.email}
              onChange={handleChange}
            />
          </div>
          <div className='inputBox'>
            <input
              type='password'
              name='password'
              placeholder='Password'
              value={formData.password}
              onChange={handleChange}
            />
          </div>
          <button type='submit'>Login</button>
        </form>
        <div className='forget'>
          <label htmlFor='remember-me'>
            <input type='checkbox' id='remember-me' />Remember Me
          </label>
          <a href='/'>Forgot Password</a>
        </div>
        <div className='register-link'>
          <p>
            Don't have an account?
            <a href="/Register">Register here</a>
          </p>
        </div>
      </div>
    </div>
  );
};
