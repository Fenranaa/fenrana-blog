import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import AdminIndex from "../views/admin/AdminIndex";
import BlogWrite from "../views/admin/components/BlogWrite";
import Dashboard from "../views/admin/components/Dashboard";
import BlogList from "../views/admin/components/BlogList";
import BlogCategories from "../views/admin/components/BlogCategories";
import BlogTag from "../views/admin/components/BlogTag.vue";
import Login from "../views/login";
import Attachment from "../views/admin/components/Attachment";
import Cookies from "js-cookie";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: Home
  },
  {
    path: "/login",
    name: "login",
    component: Login
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
    children: [
      {
        path: "/admin/dashboard",
        component: Dashboard,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/blog/list",
        component: BlogList,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/blog/write",
        component: BlogWrite,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/blog/categories",
        component: BlogCategories,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/blog/tag",
        component: BlogTag,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/attachment",
        component: Attachment,
        meta: {
          requireAuth: true
        }
      }
    ]
  }
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) {
    //我需要进行权限验证
    if (Cookies.get("loginToken")) {
      //有cookies，放行
      next();
    } else {
      //mei有cookies，跳转到登录界面
      next({
        path: "/login",
        query: { redirect: to.fullPath }
      });
    }
  } else {
    next();
  }
});

export default router;
