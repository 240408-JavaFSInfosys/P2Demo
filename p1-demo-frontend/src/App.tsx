import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login } from './components/Login/Login';
import { Catch } from './components/Catch/Catch';
import { Register } from './components/Login/Register';
import { Collection } from './components/Collection/Collection';

//If we want a component to render as soon as the app starts...
//...then you should leave the path as ""
function App() {
  return (
    <div className="App">
      <BrowserRouter>
          <Routes>
              <Route path="" element={<Login/>}/>
              <Route path="/catch" element={<Catch/>}/>
              <Route path="/register" element={<Register/>}/>
              <Route path="/collection" element={<Collection/>}/>
          </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
