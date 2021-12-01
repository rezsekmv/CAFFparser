import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginService from '../services/LoginService';
import Color from '../styles/Color';
import InputField from './InputField';

interface LoginProps {
  isLoggedIn: Function
}

const Login = ({isLoggedIn}: LoginProps) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleClick = (e: any) => {
    LoginService.login(username, password);
    isLoggedIn(true);
    navigate('/');
  };

  return (
    <div className="mx-auto" style={loginBoxStyle}>
      <h1 style={titleStyle}>Bejelentkezés</h1>
      <InputField
        inputType={'text'}
        placeholder={'Felhasználónév'}
        classes={''}
        handleOnChange={setUsername}
      />
      <InputField
        inputType={'password'}
        placeholder={'Jelszó'}
        classes={''}
        handleOnChange={setPassword}
      />

      <div>
        <button onClick={(e) => handleClick(e)} style={buttonStyle}>
          Bejelentkezés
        </button>
      </div>
    </div>
  );
};

const loginBoxStyle = {
  backgroundColor: Color.dark,
  padding: '2rem',
  marginTop: '20vh',
  maxWidth: '500px',
};

const titleStyle = {
  color: Color.white,
};

const buttonStyle = {
  color: Color.white,
  backgroundColor: Color.red,
  padding: '.5rem 2rem .5rem 2rem',
  margin: '.7rem .3rem .2rem .3rem',
};

export default Login;
