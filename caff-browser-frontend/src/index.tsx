import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import { TokenService } from './services/TokenService';
import { OpenAPI } from './services/openapi';

/*axios.interceptors.request.use(
  (config) => {
    console.log(
      `${config.method?.toUpperCase()} Request made to ${
        config.url
      } with data:`,
      config.data
    );
    return config;
  },
  (err) => {
    console.log(err);
    return err;
  }
);

axios.interceptors.response.use(
  (response) => {
    const { status, data, config } = response;
    console.log(`Response from ${config.url}:`, {
      code: status,
      ...data,
    });
    return response;
  },
  async (error) => {
    if (error.response) {
      const { status, data } = error.response;

      switch (status) {
        case 401:
          // check if 401 error was token
          // token has expired;
          try {
            // attempting to refresh token;
            await TokenService.refreshToken();
            // token refreshed, reattempting request;
            const config = error.config;
            // configure new request in a new instance;
            return await axios({
              method: config.method,
              url: config.url,
              data: config.data,
            });
          } catch (e) {
            console.log(e);
            return;
          }
        default:
          return Promise.reject(error);
      }
    } else {
      return Promise.reject(error);
    }
  }
);*/

OpenAPI.TOKEN = TokenService.getAccessToken();
ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
