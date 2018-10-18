const express = require('express');
const bodyParser = require('body-parser');
const routes = require('./routes'); // Imports routes for the products
const mongoose = require('mongoose');

hereCreds = process.argv.slice(2);

mongoose.connect('mongodb://localhost:27017/locations', {
    useNewUrlParser: true
});

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: false
}));

app.use(express.static(__dirname + '/assets'));

app.use('/map', routes);
app.use('/creds', (req, res) => res.json({
    appId: hereCreds[0],
    appCode: hereCreds[1]
}))

const port = 9000;
app.listen(port, () => {
    console.log('Server is up and running on port number ' + port);
});