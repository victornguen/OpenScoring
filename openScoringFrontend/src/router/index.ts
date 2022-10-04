import { createRouter, createWebHistory } from "vue-router";

const routes: CustomPath = [
  {
    path: "/",
    name: "SentRequests",
    component: "SentRequests",
  },
  "Recommendations",
  "LoanProcessing",
].map((x) => {
  return {
    path: `/${x?.path || x}`,
    name: x?.name || x,
    component: async () => await import(`../views/${x?.component || x}.vue`),
  };
});

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: routes,
});

export default router;
