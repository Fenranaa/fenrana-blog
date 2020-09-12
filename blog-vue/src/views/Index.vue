<template>
  <div class="main-wrapper">
    <Header />
    <div class="center">
      <el-row :gutter="20" type="flex" justify="center">
        <el-col :xs="22" :sm="20" :md="13" :lg="12">
          <router-view />
        </el-col>
        <el-col :xs="0" :sm="1" :md="4" :lg="4" class="hidden-sm-only">
          <Sidebar />
        </el-col>
      </el-row>
    </div>
    <Foot />
  </div>
</template>

<script>
import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import "element-ui/lib/theme-chalk/display.css";
import Foot from "../components/Foot";

export default {
  name: "Index",
  components: {
    Header,
    Foot,
    Sidebar
  }
};
</script>

<style scoped lang="scss">
@import "../assets/styles/common.scss";
.main-wrapper {
  .center {
    .el-row {
      margin: 0 !important;
    }
  }
}
</style>
