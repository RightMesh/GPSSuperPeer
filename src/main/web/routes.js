const express = require('express');
const routes = express.Router();

const gpsController = require('./controllers/gpsCoordinatesController');


// a simple test url to check that all of our files are communicating correctly.
routes.get('/coords', gpsController.gpsTest);
module.exports = routes;