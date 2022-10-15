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
      :type="alertType"
      transition="slide-y-transition"
    >
    {{ alertMessage }}</v-alert>
    <v-col class="px-0">
      <v-slider
        messages="от 20 000 до 10 000 000"
        label="Сумма кредита"
        class="align-center"
        v-model="sum"
        :max="10000000"
        :min="min"
        :step="step"
      >
        <template v-slot:append>
          <v-text-field
            v-model="sum"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 90px"
          ></v-text-field>
        </template>
      </v-slider>
    </v-col>
    <v-col class="px-0">
    <v-slider
        messages="от 6 месяцев до 12 месяцев"
        label="Срок кредита"
        class="align-center"
        v-model="periodInMonth"
        :max="12"
        :min="6"
        :step="1"
      >
        <template v-slot:append>
          <v-text-field
            v-model="periodInMonth"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 90px"
          ></v-text-field>
        </template>
      </v-slider>
    </v-col>
    <v-select
      v-model="currency"
      :items="items"
      label="Валюта"
    ></v-select>
    <v-text-field
      v-model="firstName"
      hide-details="auto"
      label="Имя"
      required
    ></v-text-field>
    <v-text-field
      v-model="middleName"
      label="Фамилия"
      required
    ></v-text-field>
    <v-text-field
      v-model="lastName"
      label="Отчестово"
      required
    ></v-text-field>

    <v-text-field
      v-model="mobileNumber"
      label="Телефон"
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
        :return-value.sync="dateOfBirth"
        transition="scale-transition"
        offset-y
        min-width="auto"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="dateOfBirth"
            label="Дата рождения"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker
          v-model="dateOfBirth"
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
            @click="$refs.menu.save(dateOfBirth)"
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
import RequestService from "@/services/RequestService";
export default {
  name: 'LoanProcessing',
  data: () =>   ({
    fields: [
      'sum',
      'currency',
      'periodInMonth',
      'firstName',
      'middleName',
      'lastName',
      'mobileNumber',
      'email',
      'dateOfBirth',
    ],

    currency: "",
    sum: 0,
    periodInMonth: 0,
    firstName: "",
    middleName: "",
    lastName: "",
    mobileNumber: "",
    email: "",
    dateOfBirth: null,

    dateMenu: false,
    alert: false,
    timout: null,
    items:["RUB", "USD", "EUR", "JPY", "NZD", "SEK", "GBP", "DKK", "NOK", "SGD", "CZK", "HKD", "MXN", "TRY", "CNH"],
    alertType: 'success',
    alertMessage: null,
    successMessage: 'Данные успешно отправлены',
    errorMessage: 'Ошибка',
  }),

  computed: {
    step() {
      if (this.sum < 1000000) {
        return 10000
      }
      return 100000
    },

    min() {
      if (this.sum < 1000000) {
        return 20000
      }
      return 0
    },
  },

  methods: {
    formatDate (date) {
        if (!date) return null

        const [year, month, day] = date.split('-')
        return `${day}.${month}.${year}`
      },

    async submit() {
      clearTimeout(this.timout)

      const sendData = {};
      this.fields.forEach(x => {
        if (
         x === 'dateOfBirth'
        ) {
          sendData[x] = this.formatDate(this[x])
        } else {
          sendData[x] = this[x]
        }
        })
        try {
          const response = await RequestService.createCreditApplication(sendData)
          this.alertMessage = this.successMessage;
          this.alertType = "success"

          this.$store.dispatch('addApplication', {applicationId: response.createdCreditApplicationId})
        } catch(e) {
          this.alertMessage = `${this.errorMessage}: ${e}`;
          this.alertType = "error"
        } finally {
          this.sum = 0,
          this.currency = "",
          this.periodInMonth = 0,
          this.firstName = "";
          this.middleName = "";
          this.lastName = "";
          this.mobileNumber = "";
          this.email = "";
          this.dateOfBirth = null
          
          this.dateMenu = null;
          this.alert = true;
          this.timout = window.setTimeout(() => {
            this.alert = false;
          }, 2000)
        }
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
