import { useState } from 'react';
import Color from '../styles/Color';
import InputField from './InputField';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleClick = (e: any) => {
    console.log('username: ' + username);
    console.log('password: ' + password);
  };

  return (
      <div className='mx-auto' style={loginBoxStyle}>
        <h1 style={titleStyle}>Bejelentkezés</h1>
        <InputField inputType={'text'} placeholder={'Felhasználónév'} classes={''} handleOnChange={setUsername}/>
        <InputField inputType={'password'} placeholder={'Jelszó'} classes={''} handleOnChange={setPassword}/>
        
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

const inputStyle = {
  backgroundColor: Color.white,
  padding: '.5rem ',
  margin: '.7rem .3rem .2rem .3rem',
  width: '20rem',
};

const buttonStyle = {
  color: Color.white,
  backgroundColor: Color.red,
  padding: '.5rem 2rem .5rem 2rem',
  margin: '.7rem .3rem .2rem .3rem',
};

export default Login;
