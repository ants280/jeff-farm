var express = require('express');
var path = require('path');

var app = express();

// Serve only the static files form the dist directory
var app_name = 'jeff-farm-ui'
app.use(express.static(__dirname + '/dist/' + app_name));
app.get('/*', function(req,res) { 
    res.sendFile(path.join(__dirname+'/dist' + app_name + '/index.html'));
});

// Initialize the app.
var server = app.listen(process.env.PORT || 8080, function () {
    var port = server.address().port;
    console.log('App now running on port', port);
});
