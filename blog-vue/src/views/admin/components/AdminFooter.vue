<template>
  <div class="footer">
    <el-button type="warning">保存草稿</el-button>
    <el-button type="primary" @click="openDraewr()">发布</el-button>
    <el-button type="info">附件库</el-button>

    <!-- 发布的侧边栏 start-->
    <div class="drawera">
      <el-drawer
        title="文章设置"
        :visible.sync="drawer"
        :direction="direction"
        :append-to-body="true"
        :before-close="handleClose"
        size="25%"
        style="height: 100%;"
      >


      </el-drawer>
      <!-- 发布的侧边栏 end-->
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminFooter",
  data() {
    return {
      drawer: false,
      direction: "rtl",
      date: "",
      isComment: "1",
      isTop: "1",
      categoryOptions: [],
      category: "",
      tagOptions: [],
      tag: "",
      summary: "",
      img: "//i.loli.net/2019/05/05/5ccf007c0a01d.png"
    };
  },
  created() {
    // this.initCategory();
  },
  methods: {
    handleClose(done) {
      done();
    },
    //标签的初始化方法
    initTag() {
      this.axios
        .get("http://localhost:8081/admin/tags")
        .then(response => {
          window.console.log(response.data);
          this.tagOptions = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //分类的初始化方法
    initCategory() {
      this.axios
        .get("http://localhost:8081/admin/categorys")
        .then(response => {
          window.console.log(response.data);
          this.categoryOptions = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //侧边栏打开时的回调
    openDraewr() {
      window.console.log("侧边栏打开");
      this.drawer = true;
      //做个判断防止多次请求服务器
      if (!this.categoryOptions.length >= 1) {
        this.initCategory();
      }
      if (!this.tagOptions.length >= 1) {
        this.initTag();
      }
    }
  }
};
</script>

<style scoped lang="less">
.footer {
  /*float: left;*/
}
.date {
  padding: 0 24px 10px 24px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  span {
    margin-bottom: 10px;
  }
}
.comment {
  padding: 0px 24px 10px 24px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  span {
    margin-bottom: 15px;
  }
  .group {
    margin-left: 18px;
  }
  .classify {
  }
}
.drawera {
  overflow: auto !important;
}
</style>
