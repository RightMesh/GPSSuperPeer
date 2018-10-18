const express = require('express');
const bodyParser = require('body-parser');
const routes = require('./routes'); // Imports routes for the products

const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use('/map', routes);

const port = 9000;
app.listen(port, () => {
    console.log('Server is up and running on port number ' + port);
});