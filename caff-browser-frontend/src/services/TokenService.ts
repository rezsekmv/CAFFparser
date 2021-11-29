const ACCESS_TOKEN = 'accessToken';
const REFRESH_TOKEN = 'refreshToken';

// @ts-ignore
const TokenService = {
    saveAccessToken: (token: string) => {
        sessionStorage.setItem(ACCESS_TOKEN, token);
    },
    getAccessToken: (token: string) => {
        sessionStorage.getItem(ACCESS_TOKEN);
    },
    removeAccessToken: (token: string) => {
        sessionStorage.removeItem(ACCESS_TOKEN)
    },
    saveRefreshToken: (token: string) => {
        localStorage.setItem(REFRESH_TOKEN, token);
    },
    getRefreshToken: (token: string) => {
        localStorage.getItem(REFRESH_TOKEN);
    },
    removeRefreshToken: (token: string) => {
        localStorage.removeItem(REFRESH_TOKEN)
    },

    getAccessTokenWithRefreshToken( () => {

    })
}