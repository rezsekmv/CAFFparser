import axios from 'axios';

const LoginService = {
    login: (username: string, password: string) => {
        axios.post('/auth/login', {
            username,
            password
        });
    }
}

export default LoginService;