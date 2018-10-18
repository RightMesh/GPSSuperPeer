const GPSCoordinates = require('../models/gpsCoordinates');

exports.gpsTest = (req, res) => {
    GPSCoordinates.find({}, (err, coords) => {
        res.send(coords);
    })
};