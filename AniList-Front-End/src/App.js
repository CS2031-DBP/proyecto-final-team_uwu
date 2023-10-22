import './App.css';
import { AnimeDetails } from './components/AnimeDetails';
import HeaderComponent from './components/Header/HeaderComponent';
import { Index } from './components/Index';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <HeaderComponent />
          <Routes>
          <Route exact path='/' element={<Index/>}></Route>
          <Route path="/anime/:id" element={<AnimeDetails />}></Route>
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
