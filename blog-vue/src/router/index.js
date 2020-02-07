import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import AdminIndex from "../views/admin/AdminIndex";
import BlogWrite from "../views/admin/components/BlogWrite";
import Dashboard from "../views/admin/components/Dashboard";
import BlogList from "../views/admin/components/BlogList";
import BlogCategories from "../views/admin/components/BlogCategories";
import BlogTag from "../views/admin/components/BlogTag.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/admin",
    name: "admin",
    component: AdminIndex,
    redirect: "/admin/dashboard",
    beforeEnter(to, form, next) {
      window.console.log("我是dash的路由守护");
      next();
    },
    children: [
      {
        path: "/admin/dashboard",
        component: Dashboard
      },
      {
        path: "/admin/blog/list",
        component: BlogList
      },
      {
        path: "/admin/blog/write",
        component: BlogWrite
      },
      {
        path: "/admin/blog/categories",
        component: BlogCategories
      },
      {
        path: "/admin/blog/tag",
        component: BlogTag
      }
    ]
  }
];

const router = new VueRouter({
  routes
});

export default router;
