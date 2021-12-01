import { OpenAPI } from "./openapi";

const StaticService = {
    getImage: (gifPath: string) => {
        //return 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        return OpenAPI.BASE + gifPath;
    }
}

export default StaticService;