import React from 'react';
import './App.css';
import Login from './components/Login';
import Register from './components/Register';
import Header from './components/Header';
import Footer from './components/Footer';
import Color from './styles/Color';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <div className="App" style={globalStyle}>
      <Router>
        <Header></Header>
        <div className="container">
          <Routes>
            <Route path="/login" element={<Login />}></Route>
            <Route path="/register" element={<Register />}></Route>
          </Routes>
        </div>
      </Router>
      <Footer></Footer>
    </div>
  );
}

const globalStyle = {
  color: Color.dark,
};

export default App;
