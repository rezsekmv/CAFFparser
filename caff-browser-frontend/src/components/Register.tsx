import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthService } from '../services/openapi/services/AuthService';
import Color from '../styles/Color';
import InputField from './InputField';

const Register = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [emailValidator, setEmailValidator] = useState('');
  const [emailValidatorMessage, setEmailValidatorMessage] = useState('');
  const [passwordValidator, setPasswordValidator] = useState('');
  const [passwordValidatorMessage, setPasswordValidatorMessage] = useState('');

  const navigate = useNavigate();

  const handleRegister = (e: any) => {
    if ([handleSecondEmailChange(), handleSecondPasswordChange()].every(Boolean)) {
      AuthService.signUp({
        username,
        password,
        email,
        name,
      })
        .then((user) => {
          navigate('/login');
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  const handleSecondEmailChange = (): boolean => {
    if (email === emailValidator) {
      setEmailValidatorMessage('');
      return true;
    } else {
      setEmailValidatorMessage('The two emails are different');
      return false;
    }
  };

  const handleSecondPasswordChange = (): boolean => {
    if (password === passwordValidator) {
      setPasswordValidatorMessage('');
      return true;
    } else {
      setPasswordValidatorMessage('The two passwords are different');
      return false;
    }
  };

  return (
    <div className="container" style={registerBoxStyle}>
      <h1>Register</h1>
      <div className="row">
        <InputField
          inputType={'text'}
          placeholder={'Username'}
          classes={'col-12'}
          handleOnChange={setUsername}
          validatorText=""
        />
        <InputField
          inputType={'email'}
          placeholder={'Email'}
          classes={'col-6'}
          handleOnChange={setEmail}
          validatorText=""
        />
        <InputField
          inputType={'email'}
          placeholder={'Email again'}
          classes={'col-6'}
          handleOnChange={setEmailValidator}
          validatorText={emailValidatorMessage}
        />
        <InputField
          inputType={'password'}
          placeholder={'Password'}
          classes={'col-6'}
          handleOnChange={setPassword}
          validatorText=""
        />
        <InputField
          inputType={'password'}
          placeholder={'Password again'}
          classes={'col-6'}
          handleOnChange={setPasswordValidator}
          validatorText={passwordValidatorMessage}
        />
        <InputField
          inputType={'text'}
          placeholder={'Full Name'}
          classes={'col-12'}
          handleOnChange={setName}
          validatorText=""
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
