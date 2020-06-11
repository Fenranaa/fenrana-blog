<template>
  <el-container>
    <el-aside width="initial">
      <admin-aside />
    </el-aside>
    <el-container>
      <el-header height="50px">
        <Header />
      </el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import AdminAside from "./components/AdminAside";
import Header from "./components/Header";
import { mapGetters, mapState } from "vuex";
export default {
  name: "AdminIndex",
  components: {
    AdminAside,
    Header
  },
  computed: mapState({
    opened: state => state.asideBar.opened
  }),
  methods: {
    ...mapGetters(["getAsideBar"]),
    add() {
      window.console.log("----");
      this.$bus.$emit("addBlog", "我是标题");
    }
  }
};
</script>

<style scoped lang="scss">
.el-container {
  height: 100%;
  .el-header {
    padding: 0;
  }
  .el-footer {
    background-color: #b3c0d1;
    color: #333;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }

  .el-aside {
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
  }
}
</style>
