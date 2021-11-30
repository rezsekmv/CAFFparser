import axios from 'axios';
import { TokenService } from './TokenService';

axios.interceptors.request.use(
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
);
