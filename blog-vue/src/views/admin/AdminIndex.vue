<template>
  <el-container class="con">
    <el-aside width="256px">
      <AdminAside @lala="lalaa"></AdminAside>
    </el-aside>
    <el-container>
      <el-header>Header</el-header>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import AdminAside from "./components/AdminAside";
// import AdminFooter from "./components/AdminFooter";
export default {
  name: "AdminIndex",
  components: {
    AdminAside
  },
  methods: {
    add() {
      window.console.log("----");
      this.$bus.$emit("addBlog", "我是标题");
    },
    lalaa(data) {
      window.console.log(data);
    }
  }
};
</script>

<style scoped lang="less">
.el-container {
  height: 100%;
  .el-header {
    background-color: #b3c0d1;
    color: #333;
  }
  .el-footer {
    background-color: #b3c0d1;
    color: #333;
    /*line-height: 60px;*/
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }

  .el-aside {
    background-color: #d3dce6;
    color: #333;
    /*text-align: center;*/
  }

  .el-main {
    background-color: #e9eef3;
    color: #333;
  }
}
</style>
