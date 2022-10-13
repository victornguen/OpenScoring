<template>
  <div id="app">
    <v-app id="inspire">
      <div id="nav">
          <router-link to="/">Оформление кредита</router-link> |
          <router-link to="/permissionRequest">Авторизация</router-link> |
        <router-link to="/sentRequests">Отправленные заявки</router-link> |
          <router-link to="/recommendations">Рекомендации</router-link>
      </div>
      <router-view/>
    </v-app>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #3a87d3;
    }
  }
}
</style>
