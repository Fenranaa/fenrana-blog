<template>
  <div id="main">
    <el-input v-model="title" class="title" placeholder="博客标题"></el-input>
    <div class="conter">
      <mavon-editor v-model="blog" style="height: 100%" />
    </div>
    <div class="footer">
      <el-button type="warning">保存草稿</el-button>
      <el-button type="primary" @click="openDraewr()">发布</el-button>
      <el-button type="info">附件库</el-button>
    </div>
    <!--抽屉 start-->
    <drawer
      :visible.sync="dialogVisible"
      :headerShow="true"
      header-background="#f5f5f5"
      title-color="#000"
      main-background="#EBEEF5"
      :footerShow="true"
      footer-height="60px"
      footer-background="#f5f5f5"
      width="500px"
      height="300px"
      align="right"
      close-on-click-modal
      :loading.sync="loading"
      loadingColor="#ff6700"
      title="文章设置"
    >
      <!--内容区 start-->
      <div>
        <!--日期选择 start-->
        <div class="date">
          <span class="demonstration">发表时间 :</span>
          <el-date-picker
            v-model="date"
            type="datetime"
            placeholder="选择日期时间"
            style="width: 420px;"
          >
          </el-date-picker>
        </div>
        <!--日期选择 end-->
        <el-divider></el-divider>
        <!--评论 start-->
        <div class="comment">
          <span>是否开启评论：</span>
          <el-radio-group v-model="isComment" class="group">
            <el-radio :label="0">开启评论</el-radio>
            <el-radio :label="1">关闭评论</el-radio>
          </el-radio-group>
        </div>
        <!--评论 end-->
        <el-divider></el-divider>
        <!--置顶 start-->
        <div class="comment">
          <span>是否置顶：</span>
          <el-radio-group v-model="isTop" class="group">
            <el-radio :label="0">开启</el-radio>
            <el-radio :label="1">关闭</el-radio>
          </el-radio-group>
        </div>
        <!--置顶 end-->
        <el-divider></el-divider>
        <!--分类选择 start-->
        <div class="comment">
          <span>分类：</span>
          <el-select
            v-model="category"
            placeholder="请选择"
            style="width: 420px;"
          >
            <el-option
              v-for="item in categoryOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <!--分类选择 end-->
        <el-divider></el-divider>
        <!--标签 start-->
        <div class="comment">
          <span>标签</span>
          <el-select
            v-model="tag"
            multiple
            placeholder="请选择"
            style="width: 420px;"
          >
            <el-option
              v-for="item in tagOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </div>
        <!--标签 end-->
        <el-divider></el-divider>
        <!--文章摘要 start-->
        <div class="comment">
          <span>文章摘要</span>
          <el-input
            type="textarea"
            :rows="4"
            placeholder="不填写将自动生成"
            v-model="summary"
          >
          </el-input>
        </div>
        <!--文章摘要 end -->
        <el-divider></el-divider>
        <!--缩略图 start-->
        <div class="comment">
          <span>缩略图</span>
          <el-image :src="cover"></el-image>
          <div class="aaa">
            <el-button type="success" plain>附件库</el-button>
            <el-button type="info" plain>上传附件</el-button>
          </div>
          <el-input v-model="cover2" placeholder="可以输入封面图片地址">
          </el-input>
        </div>
        <!--缩略图 end-->
      </div>
      <!--内容区 end -->
      <!--抽屉底部 start-->
      <div slot="footer" class="drawer-footer">
        <el-button type="warning">保存草稿</el-button>
        <el-button type="primary" @click="addArticle">发布</el-button>
      </div>
      <!--抽屉底部 end-->
    </drawer>
    <!--抽屉 end-->
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
// import AdminFooter from "./AdminFooter";
import drawer from "./drawer";
export default {
  data() {
    return {
      date: "",
      isComment: 1,
      isTop: 1,
      categoryOptions: [],
      category: "",
      tagOptions: [],
      tag: "",
      summary: "",
      cover: "//i.loli.net/2019/05/05/5ccf007c0a01d.png",
      cover2: "",
      loading: false,
      dialogVisible: false //控制抽屉
    };
  },
  components: {
    drawer
  },
  created() {},
  methods: {
    ...mapActions(["setTitleAsyns", "SetBlogAsyns"]),
    ...mapMutations(["setTitle"]),
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
      this.dialogVisible = true;
      //做个判断防止多次请求服务器
      if (!this.categoryOptions.length >= 1) {
        this.initCategory();
      }
      if (!this.tagOptions.length >= 1) {
        this.initTag();
      }
    },
    //文章提交方法
    addArticle() {
      //校验标题
      if (this.title.length <= 0) {
        this.$notify.error({
          title: "错误",
          message: "标题不能为空"
        });
        return;
      }
      //校验时间
      if (this.date.length <= 0) {
        this.$notify.error({
          title: "错误",
          message: "请选择时间"
        });
        return;
      }
      //校验分类
      if (this.category.length <= 0) {
        this.$notify.error({
          title: "错误",
          message: "请选择分类"
        });
        return;
      }
      //校验标签
      if (this.tag.length <= 0) {
        this.$notify.error({
          title: "错误",
          message: "请选择标签"
        });
        return;
      }
      var article = {
        title: this.title,
        createTime: this.date,
        publishTime: this.date,
        disallowComment: this.isComment,
        isTop: this.isTop,
        tag: this.tag,
        category: this.category,
        content: this.blog,
        summary: this.summary
      };
      // article.title = this.title;
      // article.createTime = this.date;
      // article.publishTime = this.date;
      // article.disallowComment = this.isComment;
      // article.isTop = this.isTop;
      // article.tag = this.tag.toString();
      // article.category = this.category;
      // article.content = this.blog;
      // article.summary = this.summary;
      //TODO 这个需要处理
      article.cover = this.cover;
      window.console.log(article);
      this.axios
        .post("http://localhost:8081/admin/addArticle", article)
        .then(response => {
          if (response.data.code === 200) {
            this.$message({
              type: "success",
              message: "添加成功!"
            });
          } else {
            this.$message({
              type: "info",
              message: "添加失败!"
            });
          }
        })
        .catch(error => {
          window.console.log(error);
        });
    }
  },
  computed: {
    ...mapState(["title"]),
    // ...mapGetters(["money"]),
    title: {
      get() {
        return this.$store.state.title;
      },
      set(value) {
        this.setTitleAsyns(value);
      }
    },
    blog: {
      get() {
        return this.$store.state.blog;
      },
      set(value) {
        this.SetBlogAsyns(value);
      }
    }
  }
};
</script>

<style lang="less" scoped>
#main {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-end;
  .title {
    height: 5%;
  }
  .conter {
    height: 83%;
    width: 100%;
  }
  .footer {
    height: 8%;
    display: flex;
    justify-content: flex-end;
    align-items: center;
  }
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
  .aaa {
    margin-top: 8px;
    margin-bottom: 8px;
  }
}

.drawer-footer {
  display: flex;
  justify-content: flex-end;
  align-items: center;
}
</style>
