import React from 'react';
import './Login.css'

export const Login = () => {
  return (
    <div className="log-in">
      <div className="log-in__wrapper">
        <div className="log-in__header">
          <h1><span>Log in</span></h1>
        
        </div>
        <form className="log-in__form">
          <label htmlFor="username" className="log-in__form-label">Username</label>
          <input type="text" placeholder="username" className="log-in__form-input" id="username" />
          <input type="password" placeholder="password" className="log-in__form-input" id="password" />
          <button type="submit" className="log-in__form-submit">Log-in</button>
        </form>
        <div className="log-in__action">
          <a href="javascript:;">Forgot password?</a>
          <a href="javascript:;">Sign up</a>
        </div>
      </div>
    </div>
  );
}

