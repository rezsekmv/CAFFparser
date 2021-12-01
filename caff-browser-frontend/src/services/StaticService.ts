import { OpenAPI } from './openapi';
const fs = require('fs');
const path = require('path');
const axios = require('axios').default;

const StaticService = {
  getImage: (imagePath: string) => {
    return OpenAPI.BASE + imagePath;
  },
  getDownloadName: (caffPath: string) => {
      return caffPath.substring(caffPath.length-11);
  }
};

export default StaticService;
