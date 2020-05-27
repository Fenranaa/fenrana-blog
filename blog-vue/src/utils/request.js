import axios from "axios";
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
instance.interceptors.response.use(response => {
  /* window.console.log("全局响应拦截");
  window.console.log(response);*/
  return response;
});

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
