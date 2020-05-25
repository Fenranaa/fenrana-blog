<template>
  <el-col>
    <!--    <h5>默认颜色</h5>-->
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
    >
      <el-menu-item index="1" @click="toDashboard">
        <i class="iconfont icon-ziyuan icon-left-a"></i>
        <span slot="title">&ensp;&ensp;&ensp;&ensp;仪表盘</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="iconfont icon-16 icon-left-a"></i>
          <span>文章</span>
        </template>
        <el-menu-item index="2-1" @click="toPage('/admin/blog/list')">
          <i class="iconfont icon-16 icon-left-a"></i>
          <span slot="title">所有文章</span>
        </el-menu-item>
        <el-menu-item index="2-2" @click="toPage('/admin/blog/write')">
          <i class="el-icon-setting"></i>
          <span slot="title">写文章</span>
        </el-menu-item>
        <el-menu-item index="2-3" @click="toPage('/admin/blog/categories')">
          <i class="el-icon-setting"></i>
          <span slot="title">分类目录</span>
        </el-menu-item>
        <el-menu-item index="2-4" @click="toPage('/admin/blog/tag')">
          <i class="el-icon-setting"></i>
          <span slot="title">标签</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="3" @click="toPage('/admin/attachment')">
        <i class="el-icon-menu"></i>
        <span slot="title">附件管理</span>
      </el-menu-item>
    </el-menu>
  </el-col>
</template>

<script>
export default {
  name: "AdminAside",

  methods: {
    handleOpen(key, keyPath) {
      window.console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      window.console.log(key, keyPath);
    },
    toPage(url) {
      this.$router.push({ path: url });
    },
    toDashboard() {
      this.$emit("lala", { msg: "hello" });
      this.$router.push({ path: "/admin/dashboard" });
    }
  }
};
</script>

<style lang="less" scoped>
.icon-left-a {
  margin-right: 5px;
}
</style>
