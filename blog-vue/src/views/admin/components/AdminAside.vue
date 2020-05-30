<template>
  <el-col>
    <!--    <h5>默认颜色</h5>-->
    <div class="login">
      <h1>Fenrana</h1>
      <h1 style="margin-left: 10px">Dashboard</h1>
    </div>
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      background-color="#d3dce6"
    >
      <el-menu-item index="1" @click="toDashboard">
        <i class="iconfont icon-yibiaopan icon-left-a"></i>
        <span slot="title">&ensp;&ensp;&ensp;&ensp;仪表盘</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="iconfont icon-wenzhang1 icon-left-a"></i>
          <span>文章</span>
        </template>
        <el-menu-item index="2-1" @click="toPage('/admin/blog/list')">
          <i class="iconfont icon-wenzhang1 icon-left-a"></i>
          <span slot="title">所有文章</span>
        </el-menu-item>
        <el-menu-item index="2-2" @click="toPage('/admin/blog/write')">
          <i class="iconfont icon-bianxie  icon-left-a"></i>
          <span slot="title">写文章</span>
        </el-menu-item>
        <el-menu-item index="2-3" @click="toPage('/admin/blog/categories')">
          <i class="iconfont icon-fenlei  icon-left-a"></i>
          <span slot="title">分类目录</span>
        </el-menu-item>
        <el-menu-item index="2-4" @click="toPage('/admin/blog/tag')">
          <i class="iconfont icon-biaoqian icon-left-a"></i>
          <span slot="title">标签</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="3" @click="toPage('/admin/attachment')">
        <span class="iconfont icon-icon_attachment icon-left-a"></span>
        <span slot="title">附件</span>
      </el-menu-item>
      <el-menu-item index="4" @click="toPage('/admin/comment')">
        <span class="iconfont icon-pinglun icon-left-a"></span>
        <span slot="title">评论</span>
      </el-menu-item>
      <el-menu-item index="5" @click="toPage('/admin/adminUser')">
        <span class="iconfont icon-xiugaitouxiang icon-left-a"></span>
        <span slot="title">用户设置</span>
      </el-menu-item>
      <el-menu-item index="6" @click="toPage('/admin/log')">
        <span class="iconfont icon-caozuorizhi icon-left-a"></span>
        <span slot="title">日志管理</span>
      </el-menu-item>
      <el-submenu index="7" background="#red">
        <template slot="title">
          <i class="iconfont icon-shezhi icon-left-a"></i>
          <span>系统</span>
        </template>
        <el-menu-item index="7-1" @click="toPage('/admin/seting')">
          <span slot="title">博客设置</span>
        </el-menu-item>
        <el-menu-item index="7-2" @click="toPage('/admin/tools')">
          <span slot="title">小工具</span>
        </el-menu-item>
        <el-menu-item index="7-3" @click="toPage('/admin/about')">
          <span slot="title">关于</span>
        </el-menu-item>
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
.login {
  height: 60px;
  background: #a6b1ae;
  color: #6a6666;
  display: flex;
  flex-direction: row;
  justify-content: center;

  h1 {
    font-size: 20px;
    font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
    font-weight: 600;
  }
}
.icon-left-a {
  /*font-size: 20px;*/
  margin-right: 5px;
}
</style>
