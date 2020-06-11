<template>
  <div class="header">
    <div class="left">
      <div class="hamburger" @click="openAsideBar">
        <span class="iconfont icon-zhankaicebianlan ceicon"></span>
      </div>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }"
          >首页</el-breadcrumb-item
        >
        <el-breadcrumb-item v-for="(item, index) in breadcrumb" :key="index">
          {{ item }}</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>
    <div class="right">
      <div>
        <el-badge is-dot class="item">
          <span class="iconfont icon-lingdang" style="font-size: 24px;"></span>
        </el-badge>
      </div>
      <div>
        <span class="iconfont icon-shezhi" style="font-size: 24px;"></span>
      </div>
      <div>
        <el-dropdown @command="handleCommand">
          <span
            class="el-dropdown-link"
            style="display: flex;justify-content: center;align-items: center; cursor: pointer"
          >
            <el-avatar
              size="small"
              icon="el-icon-user-solid"
              slot="reference"
            ></el-avatar>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-user-solid" command="touser"
              >个人中心</el-dropdown-item
            >
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import Cookies from "js-cookie";
import { mapMutations, mapState } from "vuex";
export default {
  name: "Header",
  computed: mapState({
    breadcrumb: state => state.breadcrumb
  }),
  methods: {
    ...mapMutations(["toggleAsideBar", "setBreadcrumb"]),
    openAsideBar() {
      this.toggleAsideBar();
    },
    toUser() {
      this.$router.push({ path: "/admin/adminUser" });
      this.setBreadcrumb(["用户设置"]);
    },
    logout() {
      Cookies.remove("loginToken");
      this.$router.push({ path: "/login" });
    },
    handleCommand(command) {
      if (command === "logout") {
        this.logout();
      } else if (command === "touser") {
        this.toUser();
      }
    }
  }
};
</script>

<style scoped lang="scss">
.header {
  height: 100%;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  .left {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .hamburger {
      height: 100%;
      width: 50px;
      line-height: 50px;
      text-align: center;
      cursor: pointer;
      transition: background 0.3s;
      -webkit-tap-highlight-color: transparent;
      span {
        font-size: 22px;
      }
      &:hover {
        background: rgba(0, 0, 0, 0.025);
      }
    }
  }
  .right {
    width: 10%;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: #409eff;
  }
}
</style>
