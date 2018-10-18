const mongoose = require('mongoose');
const Schema = mongoose.Schema;

let GPSCoordinatesSchema = new Schema({
    timestamp: {type: String, required: true, max: 100},
    lat: {type: Number, required: true},
    long: {type: Number, required: true},
}, { collection: 'latLong' });


// Export the model
module.exports = mongoose.model('GPSCoordinates', GPSCoordinatesSchema);