const express = require('express')
const app = express()
const cons = require('consolidate')
const path = require('path')
const os = require('os')

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

app.engine('mustache', cons.mustache);
app.set('view engine', 'mustache');
app.set('views', path.join(__dirname, '/views'));

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
app.use(express.static(path.join(__dirname, 'public')))

const port = parseInt(process.env.PORT || "8080")
app.listen(port, () => {
  console.log(`Listening on port ${port}`)
})
