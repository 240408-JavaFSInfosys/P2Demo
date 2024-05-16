import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import { Login } from './components/Login/Login';
import { Catch } from './components/Catch/Catch';
import { Register } from './components/Login/Register';
import { Collection } from './components/Collection/Collection';
import { UserProvider } from './globalData/UserProvider';

//If we want a component to render as soon as the app starts...
//...then you should leave the path as ""

//we're wrapping everything in our UserProvider to make user data global
function App() {
  return (
    <UserProvider>
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
    </UserProvider>
  );
}

export default App;
