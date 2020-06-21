import axios from "axios";
import Cookies from "js-cookie";
import { Message } from "element-ui";

// eslint-disable-next-line no-unused-vars
const instance = axios.create({
  baseURL: "http://localhost:8081",
  timeout: 5000
});

//请求拦截
//所有的网络请求都会走这个方法
instance.interceptors.request.use(config => {
  if (!config.url.search("admin")) {
    var loginToken = Cookies.get("loginToken");
    config.headers.JWTHeaderName = loginToken;
  }
  return config;
});

//添加想用拦截
instance.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    window.console.log(error.response.status);
    if (error.response.status) {
      Message.error("登录失效, 请重新登录");
      Cookies.remove("loginToken");
      this.$router.push({ path: "/login" });
    } else if (error.response.status === 403) {
      Message.error({ message: "权限不足!" });
    } else if (error.response.status === 504 || error.response.status === 404) {
      Message.error({ message: "服务器被吃了⊙﹏⊙∥" });
    } else {
      if (error.response.data.msg) {
        Message.error({ message: error.response.data.msg });
      } else {
        Message.error({ message: "未知错误!" });
      }
    }
  }
);

export const getRequest = (url, params) => {
  return instance.get(url, {
    params
  });
};

export const postRequest = (url, data) => {
  return instance.post(url, data);
};

export const delRequest = (url, data) => {
  return instance.delete(url, { data: data });
};

export const putRequest = (url, data) => {
  return instance.put(url, data);
};
