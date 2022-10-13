<template>
<v-container
  
  style="margin: 0px auto; padding: 0px; width: 40%">
  <v-form
    ref="form"
    lazy-validation
  >
  <v-alert
      v-model="alert"
      dense
      dismissible
      prominent
      type="success"
      transition="slide-y-transition"
    >
    Данные успешно отправлены</v-alert>
    <dd class="text-left" >Приложение получит разрешения на просмотр информации о ваших счетах</dd>
    <v-text-field
      v-model="login"
      hide-details="auto"
      label="Логин"
      required
    ></v-text-field>
    <v-text-field
      v-model="password"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      :type="showPassword ? 'text' : 'password'"
      name="input-10-1"
      label="Пароль"
      hint="At least 8 characters"
      counter
      @click:append="showPassword = !showPassword"
    ></v-text-field>
    <v-btn
      color="primary"
      class="mr-4"
      @click="submit"
    >
      Отправить
    </v-btn>
  </v-form>
</v-container>
</template>
<script>
export default {
  data: () => ({
    login: null,
    password: null,
    showPassword: null,
    
    
    alert: false,
    timout: null,
  }),

  methods: {
    submit() {
      clearTimeout(this.timout)

      this.login = null,
      this.password = null,
      this.showPassword = null,
      
      this.alert = true;
      this.timout = window.setTimeout(() => {
        this.alert = false;
      }, 2000)
    }
  }
}
</script>
<style>
.v-alert {
  position: fixed;
  right: 50px;
  top: 50px;
  transform: translate(-50%, -50%);
  margin: 0 auto;
}
</style>
