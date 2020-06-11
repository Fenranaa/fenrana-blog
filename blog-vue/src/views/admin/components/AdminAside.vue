<template>
  <div class="sidebar">
    <div class="logo">
      <div v-if="!opened">
        <h1>Fenrana Dashboard</h1>
      </div>
      <div v-if="opened">
        <i class="iconfont icon-wenzhang1"></i>
      </div>
    </div>
    <el-menu
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      background-color="#d3dce6"
      :collapse="opened"
    >
      <el-menu-item index="1" @click="toPage('/admin/dashboard', [])">
        <i class="iconfont icon-yibiaopan icon-left-a"></i>
        <span slot="title">仪表盘</span>
      </el-menu-item>
      <el-submenu index="2">
        <template slot="title">
          <i class="iconfont icon-wenzhang1 icon-left-a"></i>
          <span>文章</span>
        </template>
        <el-menu-item
          index="2-1"
          @click="toPage('/admin/blog/list', ['文章', '所有文章'])"
        >
          <i class="iconfont icon-wenzhang1 icon-left-a"></i>
          <span slot="title">所有文章</span>
        </el-menu-item>
        <el-menu-item
          index="2-2"
          @click="toPage('/admin/blog/write', ['文章', '写文章'])"
        >
          <i class="iconfont icon-bianxie  icon-left-a"></i>
          <span slot="title">写文章</span>
        </el-menu-item>
        <el-menu-item
          index="2-3"
          @click="toPage('/admin/blog/categories', ['文章', '文章分类'])"
        >
          <i class="iconfont icon-fenlei  icon-left-a"></i>
          <span slot="title">分类目录</span>
        </el-menu-item>
        <el-menu-item
          index="2-4"
          @click="toPage('/admin/blog/tag', ['文章', '标签'])"
        >
          <i class="iconfont icon-biaoqian icon-left-a"></i>
          <span slot="title">标签</span>
        </el-menu-item>
      </el-submenu>
      <el-menu-item index="3" @click="toPage('/admin/attachment', ['附件'])">
        <i class="iconfont icon-icon_attachment icon-left-a"></i>
        <span slot="title">附件</span>
      </el-menu-item>
      <el-menu-item index="4" @click="toPage('/admin/comment', ['评论'])">
        <i class="iconfont icon-pinglun icon-left-a"></i>
        <span slot="title">评论</span>
      </el-menu-item>
      <el-menu-item index="5" @click="toPage('/admin/adminUser', ['用户设置'])">
        <i class="iconfont icon-xiugaitouxiang icon-left-a"></i>
        <span slot="title">用户设置</span>
      </el-menu-item>
      <el-menu-item index="6" @click="toPage('/admin/log', ['日志管理'])">
        <i class="iconfont icon-caozuorizhi icon-left-a"></i>
        <span slot="title">日志管理</span>
      </el-menu-item>
      <el-submenu index="7">
        <template slot="title">
          <i class="iconfont icon-shezhi icon-left-a"></i>
          <span>系统</span>
        </template>
        <el-menu-item
          index="7-1"
          @click="toPage('/admin/seting', ['系统', '博客设置'])"
        >
          <span slot="title">博客设置</span>
        </el-menu-item>
        <el-menu-item
          index="7-2"
          @click="toPage('/admin/tools', ['系统', '小工具'])"
        >
          <span slot="title">小工具</span>
        </el-menu-item>
        <el-menu-item
          index="7-3"
          @click="toPage('/admin/about', ['系统', '关于'])"
        >
          <span slot="title">关于</span>
        </el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
import { mapState } from "vuex";
export default {
  name: "AdminAside",
  computed: mapState({
    opened: state => !state.asideBar.opened
  }),
  methods: {
    ...mapMutations(["setBreadcrumb"]),
    handleOpen(key, keyPath) {
      window.console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      window.console.log(key, keyPath);
    },
    toPage(url, data) {
      this.setBreadcrumb(data);
      this.$router.push({ path: url });
    }
  }
};
</script>

<style lang="scss" scoped>
.sidebar {
  .hidelogo {
    width: 65px;
  }
  .logo {
    overflow: hidden;
    height: 50px;
    background: #a6b1ae;
    color: #6a6666;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    h1 {
      font-size: 12px;
      font-family: Avenir, Helvetica Neue, Arial, Helvetica, sans-serif;
      font-weight: 600;
    }
  }
  .el-menu-vertical-demo {
    &:not(.el-menu--collapse) {
      width: 220px;
    }
  }
  .icon-left-a {
    margin-right: 5px;
  }
}
</style>
