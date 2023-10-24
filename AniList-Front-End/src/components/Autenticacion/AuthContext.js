import React, { createContext, useContext, useReducer } from 'react';

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

const initialState = {
  isAuthenticated: !!localStorage.getItem('authToken'),
};

function authReducer(state, action) {
  switch (action.type) {
    case 'LOGIN':
      localStorage.setItem('authToken', action.token);
      return { isAuthenticated: true };
    case 'LOGOUT':
      localStorage.removeItem('authToken');
      return { isAuthenticated: false };
    default:
      return state;
  }
}

export function AuthProvider({ children }) {
  const [state, dispatch] = useReducer(authReducer, initialState);

  return (
    <AuthContext.Provider value={{ state, dispatch }}>
      {children}
    </AuthContext.Provider>
  );
}
