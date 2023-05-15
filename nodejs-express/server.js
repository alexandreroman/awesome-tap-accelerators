const express = require('express')
const mustacheExpress = require('mustache-express');
const app = express()
const path = require('path')
const os = require('os')

app.engine('mustache', mustacheExpress());
app.set('view engine', 'mustache');
app.set('views', path.join(__dirname, '/views'));

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
const appName = pkg.name
const appVersion = pkg.version

app.get('/', (req, res) => {
  res.render('index', {
    appName: appName,
    appVersion: appVersion,
    nodeVersion: process.version,
    ipAddress: getIPAddress(),
  })
})

app.get('/health', (req, res) => {
  res.send('UP')
})

app.use('/css/bootstrap.min.css', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/css/bootstrap.min.css')))
app.use('/js/bootstrap.bundle.min.js', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/js/bootstrap.bundle.min.js')))
app.use('/js/jquery.slim.min.js', express.static(path.join(__dirname, 'node_modules/jquery/dist/jquery.slim.min.js')))
app.use(express.static(path.join(__dirname, 'public')))

const port = parseInt(process.env.PORT || "8080")
app.listen(port, () => {
  console.log(`Listening on port ${port}`)
})
