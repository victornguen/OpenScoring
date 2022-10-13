<template>
<v-container>
  <div v-for="account in accounts" :key="account.accountId">
    <div v-for="details in account.accountDetails" :key="account.accountId + details.identification">
        <v-container fluid>
          <v-row dense>
            <v-col>
              <v-card width="600">
                <v-toolbar
                  color="indigo"
                  dark
                >
                  <!-- <v-app-bar-nav-icon></v-app-bar-nav-icon> -->

                  <v-toolbar-title style="width: 100%" class="d-flex justify-space-between">
                      <div>{{details.name}}</div>
                      <div>{{account.currency}}</div>
                    </v-toolbar-title>
                  <v-spacer></v-spacer>

                  <!-- <v-btn icon>
                    <v-icon>mdi-magnify</v-icon>
                  </v-btn> -->
                </v-toolbar>
                <v-card-text class="text-left">
                    some text...
                </v-card-text>
                

                <v-card-actions>
                  <!-- <v-spacer></v-spacer>

                  <v-btn icon>
                    <v-icon>mdi-heart</v-icon>
                  </v-btn>

                  <v-btn icon>
                    <v-icon>mdi-bookmark</v-icon>
                  </v-btn>

                  <v-btn icon>
                    <v-icon>mdi-share-variant</v-icon>
                  </v-btn> -->
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
    </div>
    <!-- <div>{{ account.accountDetails.schemeName }}</div> -->
    <!-- <div>{{ account }}</div> -->
  </div>
</v-container>
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
</style>
