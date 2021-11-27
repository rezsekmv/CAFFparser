# CAFF Browser

## Starting the application

### 1 Running backend with Docker
1.1 Make sure to have Java, Maven, Docker installed (without Docker, see Section 4)
1.2 Click `start-mysql.cmd` to run the database container and wait for successful response
1.3 Click `start-be.cmd` in the root folder and wait for successful response

### 2 Running frontend
2.1 Make sure to have Node installed
2.2 Install yarn: open a console and run `npm install --global yarn`, then check if it was successful with `yarn --version`
2.3 Install react and react-scripts: navigate to `caff-browser-frontend` folder and run `npm install react-scripts --save` or `yarn add react` and `yarn add react-scripts` commands
2.4 Install typescript: in the frontend folder, run `yarn add typescript`, this might take a while
2.5 Click `start-fe.cmd` in the root folder and wait for successful response

### 3 Testing in Postman
3.1 Import `caffbrowser.postman_collection.json` to get a new Postman collection
3.2 Make sure to disable SSL validation in global settings

### 4 Running backend without Docker
4.1 Create an empty MySQL schema with the name `caffbrowser` in `root` (MySQL Workbench can be useful)
4.2 Click `start-be-localdb.cmd` in the root folder and wait for successful response
4.3 You can alter the settings in the `application-localdb.yml` profile config file in the `caff-browser-backend` folder in case you need to change the database name and password
