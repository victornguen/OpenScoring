const proxyDev = {
  "/accounts": {
    target: `http://localhost:8280/`,
  },
  "/balances": {
    target: `http://localhost:8280/`,
  },
  "/login": {
    target: `http://localhost:8070/`,
  },
};

module.exports = {
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy:  proxyDev,
  }
}
