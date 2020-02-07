import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    title: "",
    cont: 1000,
    blog: "开始编辑吧"
  },
  mutations: {
    setTitle(state, value) {
      state.title = value;
    },
    setCont(state) {
      state.cont += 1;
    },
    setBlog(state, value) {
      state.blog = value;
    }
  },
  getters: {
    //对state中的数据进行加工处理
    money: state => {
      return state.cont + "元";
    }
  },
  actions: {
    //异步操作时使用
    setTitleAsyns({ commit }, value) {
      commit("setTitle", value);
    },
    SetBlogAsyns({ commit }, value) {
      commit("setBlog", value);
    }
  },
  modules: {}
});
