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
    <v-text-field
      v-model="firstName"
      hide-details="auto"
      label="Имя"
      required
    ></v-text-field>
    <v-text-field
      v-model="surname"
      label="Фамилия"
      required
    ></v-text-field>
    <v-text-field
      v-model="patronymic"
      label="Отчестов"
      required
    ></v-text-field>

    <v-text-field
      v-model="email"
      label="E-mail"
      required
    ></v-text-field>

    <v-row>
    <v-col
    data-app
    >
      <v-menu
        ref="menu"
        v-model="dateMenu"
        :close-on-content-click="false"
        :return-value.sync="date"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="date"
            label="Picker in menu"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="date"
          no-title
          scrollable
        >
          <v-spacer></v-spacer>
          <v-btn
            text
            color="primary"
            @click="dateMenu = false"
          >
            Отменить
          </v-btn>
          <v-btn
            text
            color="primary"
            @click="$refs.menu.save(date)"
          >
            ОК
          </v-btn>
        </v-date-picker>
      </v-menu>
    </v-col>
    <v-spacer></v-spacer>
  </v-row>

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
    firstName: null,
    surname: null,
    patronymic: null,
    phone: null,
    email: null,
    birthday: null,
    dateMenu: false,
    date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
    
    alert: false,
    timout: null,
  }),

  methods: {
    submit() {
      clearTimeout(this.timout)

      this.firstName = null;
      this.surname = null;
      this.patronymic = null;
      this.phone = null;
      this.email = null;
      this.birthday = null;
      this.dateMenu = null;
      this.date = null;
      
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
