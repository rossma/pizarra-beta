module.exports = {
  entry: './vue/main.js',
  output: {
    path: './public/javascripts',
    publicPath: 'public/javascripts/',
    filename: 'bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        loader: 'babel',
        exclude: /node_modules/
      },
      {
        test: /\.vue$/,
        loader: 'vue'
      },
      { 
        test: /\.png$/, 
        loader: "url-loader?mimetype=image/png" 
      },
      // the url-loader uses DataUrls. 
      // the file-loader emits files. 
       { test: /\.jsx?$/, exclude: /(node_modules|bower_components)/, loader: 'babel' },
        { test: /\.css$/, loader: 'style-loader!css-loader' },
        { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: "file" },
        { test: /\.(woff|woff2)$/, loader:"url?prefix=font/&limit=5000" },
        { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/octet-stream" },
        { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=image/svg+xml" }
    ]
  },
  vue: {
    loaders: {
      js: 'babel'
    }
  }
}