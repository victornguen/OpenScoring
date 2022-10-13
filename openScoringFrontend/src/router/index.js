import Vue from 'vue'
import VueRouter from 'vue-router'
import SentRequests from "../views/SentRequests.vue";
import Recommendations from "../views/Recommendations.vue";
import LoanProcessing from "../views/LoanProcessing.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: "/SentRequests",
    name: "SentRequests",
    component: SentRequests,
  },
  {
    path: "/Recommendations",
    name: "Recommendations",
    component: Recommendations,
  },
  {
    path: "/",
    name: "LoanProcessing",
    component: LoanProcessing,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
