import { useState } from 'react';
import { AuthService } from '../services/openapi/services/AuthService';
import Color from '../styles/Color';
import InputField from './InputField';

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleRegister = (e: any) => {
    AuthService.signUp({
      username,
      password
    }).then(
      (user) => {
        console.log(user);
      }
    ).catch( (error) => {
      console.log(error.response.data);
    });
    console.log('username: ' + username);
    console.log('password: ' + password);
  };

  return (
    <div className="container" style={registerBoxStyle}>
      <h1>Regisztráció</h1>
      <div className="row">
        <InputField
          inputType={'text'}
          placeholder={'Felhasználónév'}
          classes={'col-12'}
          handleOnChange={setUsername}
        />
        <InputField
          inputType={'email'}
          placeholder={'Email'}
          classes={'col-6'}
          handleOnChange={setPassword}
        />
        <InputField
          inputType={'email'}
          placeholder={'Email újra'}
          classes={'col-6'}
          handleOnChange={setUsername}
        />
        <InputField
          inputType={'password'}
          placeholder={'Jelszó'}
          classes={'col-6'}
          handleOnChange={setPassword}
        />
        <InputField
          inputType={'password'}
          placeholder={'Jelszó újra'}
          classes={'col-6'}
          handleOnChange={setUsername}
        />
        <InputField
          inputType={'text'}
          placeholder={'Név'}
          classes={'col-12'}
          handleOnChange={setUsername}
        />
        <div>
          <button onClick={(e) => handleRegister(e)} style={buttonStyle}>
            Regisztráció
          </button>
        </div>
      </div>
    </div>
  );
};

const registerBoxStyle = {
  marginTop: '10vh',
};

const buttonStyle = {
  color: Color.white,
  backgroundColor: Color.red,
  padding: '.5rem 5rem .5rem 5rem',
  margin: '.7rem .3rem .2rem .3rem',
};

export default Register;
