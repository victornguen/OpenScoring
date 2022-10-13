import Vue from 'vue'
import VueRouter from 'vue-router'
import SentRequests from "../views/SentRequests.vue";
import Recommendations from "../views/Recommendations.vue";
import LoanProcessing from "../views/LoanProcessing.vue";
import PermissionRequest from "../views/PermissionRequest.vue";
Vue.use(VueRouter)

const routes = [
  {
    path: "/SentRequests",
    name: "SentRequests",
    component: SentRequests,
  },
  {
    path: "/recommendations",
    name: "Recommendations",
    component: Recommendations,
  },
  {
    path: "/",
    name: "loanProcessing",
    component: LoanProcessing,
  },
  {
    path: "/permissionRequest",
    name: "PermissionRequest",
    component: PermissionRequest,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
