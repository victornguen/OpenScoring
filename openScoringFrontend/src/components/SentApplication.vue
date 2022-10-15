<template>
<div>
  <v-toolbar color="indigo" dark>
    <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

    <v-toolbar-title
    >
    {{'Сумма: ' + application.sum +' ' + application.currency}}
    </v-toolbar-title>
    <v-spacer></v-spacer>

    <!-- <v-btn icon>
    <v-icon>mdi-magnify</v-icon>
  </v-btn> -->
  </v-toolbar>
  <v-card-text class="text-left">
    {{ 'Срок: ' + application.periodInMonth + ' месяцев' }}
  </v-card-text>

  <v-card-actions>
    <v-spacer></v-spacer>
  <v-dialog
    v-model="dialog"
    persistent
    max-width="900"
  >
    <template v-slot:activator="{ on, attrs }">
      <v-btn icon v-on="on" v-bind="attrs" @click="openDetails(application.id)">
    <v-icon>mdi-clipboard-text-outline</v-icon>
  </v-btn>
    </template>
    <v-card>
      <template v-if="dialogData && dialogData.length">
        <v-card-title class="text-h4">
          Предложения
        </v-card-title>

        <v-card v-for="(data, i) in dialogData" :key="application.id + i" class="ma-5 pa-3">
              <card :data="data" :application="application"></card>
        </v-card>
      </template>
      <v-card-title v-else class="text-h5">
          Для данной заявки предложения отсутствуют
        </v-card-title>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="primary"
          text
          @click="dialog = false"
        >
          Закрыть
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <v-btn icon @click="removeApplication(application.id)">
    <v-icon>mdi-delete</v-icon>
  </v-btn>
  </v-card-actions>
</div>
</template>
<script>
import Card from '../components/Card.vue';
import RequestService from "@/services/RequestService";
export default {
  components: { Card },
  props: ['application'],
  data: () =>({
    dialog: false,
    dialogData: [],
  }),
  
  methods: {

    async openDetails(id) {
      this.dialogData = await RequestService.loadApplicationDetails(id);
    },

    async removeApplication(id) {
      this.$emit('removeApplication', id)
    }
  }
}
</script>