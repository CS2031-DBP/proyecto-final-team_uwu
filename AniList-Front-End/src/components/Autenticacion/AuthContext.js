import React, { createContext, useContext, useReducer } from 'react';

const AuthContext = createContext();

export function useAuth() {
  return useContext(AuthContext);
}

const initialState = {
  isAuthenticated: !!localStorage.getItem('authToken'),
  id : localStorage.getItem('userId')
};

function authReducer(state, action) {
  switch (action.type) {
    case 'LOGIN':
      localStorage.setItem('authToken', action.token);
      console.log("accediendo");
      console.log(action.id);
      localStorage.setItem('userId', action.id);

      return { isAuthenticated: true};
    case 'LOGOUT':
      localStorage.removeItem('authToken');
      localStorage.removeItem('userId');
      return { isAuthenticated: false};
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
