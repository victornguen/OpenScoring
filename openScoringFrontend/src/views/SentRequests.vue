<template>
  <v-container class="d-flex flex-wrap" style="width: 60%">
    <v-card v-for="application in applications" :key="application.id" width="300" class="ma-5">
      <sent-application :application="application" @removeApplication="removeApplication"></sent-application>
    </v-card>
  </v-container>
</template>
<script>
import RequestService from "@/services/RequestService";
import SentApplication from '../components/SentApplication.vue';
export default {
  components: { SentApplication },
  name: 'SentRequests',
  data: () => ({
    applications: null,
  }),

  async created() {
    this.applications = await Promise.all(this.$store.state.sentApplications.map(element => {
      return RequestService.loadApplication(element);
    }));
  },

  methods: {
    async removeApplication(id) {
      if (await RequestService.removeApplication(id)) {
        this.$store.dispatch('removeApplication', {applicationId: id})
      }
      this.applications = await Promise.all(this.$store.state.sentApplications.map(element => {
      return RequestService.loadApplication(element);
    }));
    }
  }
};
</script>

<style>
</style>
