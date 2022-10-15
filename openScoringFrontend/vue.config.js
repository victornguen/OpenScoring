const proxyDev = {
  "/": {
    target: `http://localhost:8090/`,
  },
};
module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy:  proxyDev,
    // port:  8090,
  }
}
