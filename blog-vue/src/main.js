import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import axios from "axios";
import VueAxios from "vue-axios";
import "element-ui/lib/theme-chalk/display.css";

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(mavonEditor);
Vue.use(VueAxios, axios);
Vue.prototype.$bus = new Vue();
// Vue.prototype.axios = axios;
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
