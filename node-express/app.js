const path = require('path')

const express = require('express')
const mustacheExpress = require('mustache-express');
const app = express()
const os = require('os')

// Configure Express with Mustache support.
app.engine('mustache', mustacheExpress());
app.set('view engine', 'mustache');
app.set('views', path.join(__dirname, '/views'));

// -------------------------------
// ### BEGIN OF CUSTOM CONTENT ###

function getIPAddress() {
  const interfaces = os.networkInterfaces();
  for (var devName in interfaces) {
    const iface = interfaces[devName];

    for (var i = 0; i < iface.length; i++) {
      const alias = iface[i];
      if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal)
        return alias.address;
    }
  }
  return '0.0.0.0';
}

const pkg = require(path.join(__dirname, '/package.json'))
const indexValues = {
  appName: pkg.name,
  appVersion: pkg.version,
  nodeVersion: process.version,
  ipAddress: getIPAddress(),
}

app.get('/', (req, res) => {
  res.render('index', indexValues)
})

// ###  END OF CUSTOM CONTENT  ###
// -------------------------------

// Set up health check endpoint.
app.get('/health', (req, res) => {
  res.send('UP')
})

// Set up static content.
app.use('/css/bootstrap.min.css', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/css/bootstrap.min.css')))
app.use('/js/bootstrap.bundle.min.js', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js')))
app.use('/js/jquery.slim.min.js', express.static(path.join(__dirname, 'node_modules/jquery/dist/jquery.slim.min.js')))
app.use(express.static(path.join(__dirname, 'public')))

// Start the HTTP server.
const port = parseInt(process.env.PORT || "8080")
const server = app.listen(port, () => {
  console.log(`Listening on port ${port}`)
})

process.on('SIGTERM', () => {
  server.close()
})
