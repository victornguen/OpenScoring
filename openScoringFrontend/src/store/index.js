import Vue from 'vue'
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    sentApplications: [],
  },

  getters: {
    sentApplications({ sentApplications }) {
      return sentApplications;
    },
  },

  mutations: {
    ADD_APPLICATION(state, { id }) {
      state.sentApplications = [...state.sentApplications, id];
    },

    REMOVE_APPLICATION(state, { id }) {
      state.sentApplications = state.sentApplications.filter(x => x !==id);
    },
  },

  actions: {
    addApplication({ commit }, { applicationId }) {
      commit({
        type: 'ADD_APPLICATION',
        id: applicationId,
      });
    },

    removeApplication({ commit }, { applicationId }) {
      commit({
        type: 'REMOVE_APPLICATION',
        id: applicationId,
      });
    }
  }
});