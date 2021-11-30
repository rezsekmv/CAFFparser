import {AuthService} from "./openapi";

const ACCESS_TOKEN = 'accessToken';
const REFRESH_TOKEN = 'refreshToken';

// @ts-ignore
const TokenService = {
    saveAccessToken: (token: string) => {
        sessionStorage.setItem(ACCESS_TOKEN, token);
    },
    getAccessToken: () => {
        sessionStorage.getItem(ACCESS_TOKEN);
    },
    removeAccessToken: () => {
        sessionStorage.removeItem(ACCESS_TOKEN)
    },
    saveRefreshToken: (token: string) => {
        localStorage.setItem(REFRESH_TOKEN, token);
    },
    getRefreshToken: () => {
        localStorage.getItem(REFRESH_TOKEN);
    },
    removeRefreshToken: () => {
        localStorage.removeItem(REFRESH_TOKEN)
    },

    getAccessTokenWithRefreshToken: () => {
        AuthService.refreshToken().then(() => {
            console.log('refresh')
        })
    }
}