<template>
  <div class="about">
    <h1>This is an SentRequests page</h1>
  </div>
</template>
<script>
import SendRequestService from "@/services/SendRequestService";
export default {
  async created() {
    await SendRequestService.loadAccountsAsync();
    console.log("here");
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
