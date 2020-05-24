import axios from "axios";
import { Message } from "element-ui";
import Cookies from "js-cookie";

// eslint-disable-next-line no-unused-vars
const instance = axios.create({
  baseURL: "http://localhost:8081",
  timeout: 5000
});

//请求拦截
//所有的网络请求都会走这个方法
instance.interceptors.request.use(config => {
  if (config.url !== "/login") {
    var loginToken = Cookies.get("loginToken");
    config.headers.JWTHeaderName = loginToken;
  }
  return config;
});

//添加想用拦截
instance.interceptors.response.use(
  response => {
    /* window.console.log("全局响应拦截");
  window.console.log(response);*/
    return response;
  },
  err => {
    window.console.log("1111");
    window.console.log(err.response);
    window.console.log("222");
    if (err.response.data.status === 504 || err.response.data.status === 404) {
      Message.error({ message: "服务器被吃了⊙﹏⊙∥" });
    } else if (err.response.status === 403) {
      Message.error({ message: "权限不足,请联系管理员!" });
    } else if (err.response.data.status === 401) {
      Message.error({ message: err.response.data.msg });
    } else {
      if (err.response.data.msg) {
        Message.error({ message: err.response.data.msg });
      } else {
        Message.error({ message: "未知错误!" });
      }
    }
    return Promise.reject(err);
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

export const delRequest = url => {
  return instance.delete(url);
};

export const putRequest = (url, data) => {
  return instance.put(url, data);
};
