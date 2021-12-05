import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginService from '../services/LoginService';
import { TokenService } from '../services/TokenService';
import Color from '../styles/Color';
import InputField from './InputField';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(false);
  const navigate = useNavigate();

  const handleClick = (e: any) => {
    LoginService.login(username, password)
      .then((res) => {
        TokenService.saveAccessToken(res.data.accessToken);
        TokenService.saveRefreshToken(res.data.refreshToken);
        navigate('/');
      })
      .catch((error) => {
        setError(true);
      });
  };

  return (
    <div className="mx-auto" style={loginBoxStyle}>
      <h1 style={titleStyle}>Login</h1>
      <InputField
        inputType={'text'}
        placeholder={'Username'}
        classes={''}
        handleOnChange={setUsername}
        validatorText=''
      />
      <InputField
        inputType={'password'}
        placeholder={'Password'}
        classes={''}
        handleOnChange={setPassword}
        validatorText=''
      />

      <div>
        <button onClick={(e) => handleClick(e)} style={buttonStyle}>
          Login
        </button>
      </div>
      {error && (
        <div style={{ color: Color.red, textAlign: 'center' }}>Wrong username or password</div>
      )}
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
