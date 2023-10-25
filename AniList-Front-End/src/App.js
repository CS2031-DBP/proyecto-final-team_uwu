import './App.css';

import { AnimeDetails } from './components/AnimeDetails';
import HeaderComponent from './components/Header/HeaderComponent';
import { Index } from './components/Index';
import { Login } from './components/Login/Login';

import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './components/Autenticacion/AuthContext'; // Importa el AuthProvider
import { Register } from './components/Register/Register';
import Threads from './components/Threads/Threads';
import Thread from './components/Threads/Thread/Thread';
import { FooterComponent } from './components/Footer/FooterComponent';


function App() {
    // Verifica si el usuario está autenticado (por ejemplo, si existe un token en localStorage)
    const idUser = localStorage.getItem("userId");
    return (
    <div className="App">
    <BrowserRouter>
      <AuthProvider> {/* Envuelve la aplicación con el AuthProvider */}
        <HeaderComponent />
        <Routes>
          <Route exact path='/' element={<Index />} />
          <Route path="/anime/:id" element={<AnimeDetails />} />
          <Route path='/Login' element={<Login />} />
          <Route path='/Register' element={<Register />} />
          <Route path='/Threads' element={<Threads userId={idUser} />}/>
          <Route path='/Threads/:id' element={<Thread userId = {idUser}/>}/>
        </Routes>
        <FooterComponent/>
      </AuthProvider>
    </BrowserRouter>
  </div>
  );
}

export default App;
