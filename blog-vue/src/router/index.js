import Vue from "vue";
import VueRouter from "vue-router";
// import Home from "../views/Home.vue";
import AdminIndex from "../views/admin/AdminIndex";
import BlogWrite from "../views/admin/components/BlogWrite";
import Dashboard from "../views/admin/components/Dashboard";
import BlogList from "../views/admin/components/BlogList";
import BlogCategories from "../views/admin/components/BlogCategories";
import BlogTag from "../views/admin/components/BlogTag.vue";
import Login from "../views/login";
import Attachment from "../views/admin/components/Attachment";
import Comment from "../views/admin/components/Comment";
import Log from "../views/admin/components/Log";
import AdminUser from "../views/admin/components/AdminUser";
import Seting from "../views/admin/components/Seting";
import Tools from "../views/admin/components/Tools";
import About from "../views/admin/components/About";
import Index from "../views/Index";
import Article from "../views/Article";
import Main from "../components/Main";
import Cookies from "js-cookie";
import Archives from "../components/Archives.vue";
import Category from "../components/Category.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "index",
    component: Index,
    redirect: "/main",
    children: [
      {
        path: "/main",
        name: "main",
        component: Main
      },
      {
        path: "/archives",
        name: "archives",
        component: Archives
      },
      {
        path: "/category",
        name: "category",
        component: Category
      }
    ]
  },
  {
    path: "/article/:id",
    name: "article",
    component: Article
  },
  {
    path: "/login",
    name: "login",
    component: Login
  },
  {
    path: "/about",
    name: "about",
    component: () => import("../views/About.vue")
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
      },
      {
        path: "/admin/comment",
        component: Comment,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/log",
        component: Log,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/adminUser",
        component: AdminUser,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/seting",
        component: Seting,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/tools",
        component: Tools,
        meta: {
          requireAuth: true
        }
      },
      {
        path: "/admin/about",
        component: About,
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
