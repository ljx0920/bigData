//require('./check-versions')()

var config = require('../config')
if(!process.env.NODE_ENV) {
	process.env.NODE_ENV = JSON.parse(config.dev.env.NODE_ENV)
}

var opn = require('opn')
var path = require('path')
var express = require('express')
var webpack = require('webpack')
var proxyMiddleware = require('http-proxy-middleware')
var webpackConfig = process.env.NODE_ENV === 'testing' ?
	require('./webpack.prod.conf') :
	require('./webpack.dev.conf')

var compiler = webpack(webpackConfig)
var devMiddleware = require('webpack-dev-middleware')(compiler, {
	publicPath: webpackConfig.output.publicPath,
	quiet: true
})

var hotMiddleware = require('webpack-hot-middleware')(compiler, {
	log: () => {}
})
// force page reload when html-webpack-plugin template changes
compiler.plugin('compilation', function(compilation) {
	compilation.plugin('html-webpack-plugin-after-emit', function(data, cb) {
		hotMiddleware.publish({
			action: 'reload'
		})
		cb()
	})
})

var run = function(option) {
	var port = option.port
	var autoOpenBrowser = !!option.autoOpenBrowser
	var proxyTable = option.proxyTable
	var app = express()
	Object.keys(proxyTable).forEach(function(context) {
		var options = proxyTable[context]
		if(typeof options === 'string') {
			options = {
				target: options
			}
		}
		app.use(proxyMiddleware(options.filter || context, options))
	})
	app.use(require('connect-history-api-fallback')())
	app.use(devMiddleware)
	app.use(hotMiddleware)
	var staticPath = path.posix.join(config.dev.assetsPublicPath, config.dev.assetsSubDirectory)
	app.use(staticPath, express.static('./static'))
	var uri = 'http://127.0.0.1:' + port
	var _resolve
	var readyPromise = new Promise(resolve => {
		_resolve = resolve
	})
	devMiddleware.waitUntilValid(() => {
		if(autoOpenBrowser && process.env.NODE_ENV !== 'testing') {
			opn(uri)
		}
		_resolve()
	})
	app.listen(port)
}
for(var x in config) {
	if(config[x].port && config[x].proxyTable) {
		run(config[x])
	}
}