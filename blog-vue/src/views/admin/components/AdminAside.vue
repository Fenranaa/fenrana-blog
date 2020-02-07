<template>
  <el-col>
    <h5>默认颜色</h5>
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
    >
      <el-menu-item index="1" @click="toDashboard">
        <i class="iconfont icon-ziyuan"></i>
        <span slot="title">&ensp;&ensp;&ensp;&ensp;仪表盘</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="iconfont icon-16"></i>
          <span>&ensp;&ensp;&ensp;&ensp;文章</span>
        </template>
        <el-menu-item-group>
          <el-menu-item index="2-1" @click="toList">
            <i class="iconfont icon-16"></i>
            <span slot="title">所有文章</span>
          </el-menu-item>
          <el-menu-item index="2-2" @click="toWrite">
            <i class="el-icon-setting"></i>
            <span slot="title">写文章</span>
          </el-menu-item>
          <el-menu-item index="2-3" @click="toCategories">
            <i class="el-icon-setting"></i>
            <span slot="title">分类目录</span>
          </el-menu-item>
          <el-menu-item index="2-4" @click="toTag">
            <i class="el-icon-setting"></i>
            <span slot="title">标签</span>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
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

    toDashboard() {
      this.$emit("lala", { msg: "hello" });
      this.$router.push({ path: "/admin/dashboard" });
    },
    toList() {
      this.$router.push({ path: "/admin/blog/list" });
    },
    toWrite() {
      this.$router.push({ path: "/admin/blog/write" });
    },
    toCategories() {
      this.$router.push({ path: "/admin/blog/categories" });
    },
    toTag() {
      this.$router.push({ path: "/admin/blog/tag" });
    }
  }
};
</script>
