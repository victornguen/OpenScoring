<template>
<div>
  <div class="about">
    <h1>This is an SentRequests page</h1>
  </div>
  <div v-for="account in accounts" :key="account.accountId">
    <div v-for="(details, i) in account.accountDetails" :key="account.accountId + i">
      {{ details.name }}
      {{ details.schemeName }}
    </div>
    <!-- <div>{{ account.accountDetails.schemeName }}</div> -->
    <!-- <div>{{ account }}</div> -->
  </div>
</div>
</template>
<script>
import RequestService from "@/services/RequestService";
export default {
  data: () => ({
    accounts: null,
  }),

  async created() {
    this.accounts = await RequestService.loadAccountsAsync();
  },
};
</script>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
