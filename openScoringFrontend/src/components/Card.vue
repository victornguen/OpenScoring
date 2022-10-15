<template>
  <div>
    <v-col class="px-0">
      <v-slider
        :messages="'от ' + data.amountLowerBound + ' до ' + data.amountUpperBound"
        label="Сумма кредита"
        class="align-center"
        v-model="sum"
        :max="data.amountUpperBound"
        :min="data.amountLowerBound"
        :step="10000"
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
        :messages="'от ' + data.timeLimitMonthLower.value + ' месяцев до ' + data.timeLimitMonthUpper.value + ' месяцев'"
        label="Срок кредита"
        class="align-center"
        v-model="periodInMonth"
        :max="12"
        :min="data.timeLimitMonthLower.value"
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
    <v-col class="px-0">
      Ставка: {{ data.percentage }}%
      Ежемесячный платеж: {{ Math.round(sum * (creditBase(data)* Math.pow((1+ creditBase(data)), periodInMonth)/(Math.pow((1+ creditBase(data)), periodInMonth)-1))) + ' ' + application.currency }}
    </v-col>
  </div>
</template>
<script>
export default {
  props: ['data', 'application'],
  data: () => ({
    periodInMonth: 0,
    sum: 0,
  }),
  methods: {
    creditBase(data) {
      return data.percentage / this.periodInMonth / 100
    },
  }
}
</script>