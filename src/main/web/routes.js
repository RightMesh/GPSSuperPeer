const express = require('express');
const routes = express.Router();

const gpsController = require('./controllers/gpsCoordinatesController');

routes.get('/coords', gpsController.allCoords);
module.exports = routes;