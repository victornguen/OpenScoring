import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";

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

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue(), vueJsx()],
  resolve: {
    alias: {
      "@": fileURLToPath(new URL("./src", import.meta.url)),
    },
  },
  server: {
    proxy: proxyDev,
  },
});
