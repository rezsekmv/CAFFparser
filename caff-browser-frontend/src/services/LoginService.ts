import axios from 'axios';
import { TokenService } from './TokenService';
var querystring = require('querystring');

const LoginService = {
  login: (username: string, password: string) => {
    axios
      .post(
        '/api/login',
        querystring.stringify({
          username,
          password,
        }),
        {
          baseURL: 'http://localhost:8080',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
        }
      )
      .then((res) => {
        console.log(res.data)
        TokenService.saveAccessToken(res.data.accessToken);
        TokenService.saveRefreshToken(res.data.refreshToken);
      })
      .catch((error) => {
        console.error('Login fetch FAILED!');
        console.error(JSON.stringify(error));
      });
  },
};

export default LoginService;
