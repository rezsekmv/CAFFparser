# CAFF Browser

## Starting the application

### Running backend with Docker
- Make sure to have Java, Maven, Docker installed (without Docker, see Section 4)
- Click `start-mysql.cmd` to run the database container and wait for successful response
- Click `start-be.cmd` in the root folder and wait for successful response

### Running frontend
- Make sure to have Node installed
- Install yarn: open a console and run `npm install --global yarn`, then check if it was successful with `yarn --version`
- Install react and react-scripts: navigate to `caff-browser-frontend` folder and run `npm install react-scripts --save` or `yarn add react` and `yarn add react-scripts` commands
- Install typescript: in the frontend folder, run `yarn add typescript`, this might take a while
- Click `start-fe.cmd` in the root folder and wait for successful response

### Testing in Postman
- Import `caffbrowser.postman_collection.json` to get a new Postman collection
- Make sure to disable SSL validation in global settings

### Running backend without Docker
- Create an empty MySQL schema with the name `caffbrowser` in `root` (MySQL Workbench can be useful)
- Click `start-be-localdb.cmd` in the root folder and wait for successful response
- You can alter the settings in the `application-localdb.yml` profile config file in the `caff-browser-backend` folder in case you need to change the database name and password
