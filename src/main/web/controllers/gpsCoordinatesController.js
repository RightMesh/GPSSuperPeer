const GPSCoordinates = require('../models/gpsCoordinates');

exports.allCoords = (req, res) => {
    GPSCoordinates.find({}, (err, coords) => {
        res.send(coords);
    })
};