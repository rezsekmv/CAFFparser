import axios, { AxiosResponse } from 'axios';
var querystring = require('querystring');

const LoginService = {
  login: (username: string, password: string):Promise<AxiosResponse<any, any>>  => {
    return axios
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
  },
};

export default LoginService;
