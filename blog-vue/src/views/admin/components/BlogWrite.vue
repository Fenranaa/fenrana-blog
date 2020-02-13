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

    <!-- 侧边栏 start   -->
    <el-drawer title="文章设置" :visible.sync="dialogVisible" size="25%">
      <!--内容区 start-->
      <div class="drawer-con">
        <!--日期选择 start-->
        <div class="date">
          <span class="demonstration">发表时间 :</span>
          <el-date-picker
            v-model="date"
            type="datetime"
            placeholder="选择日期时间"
            style="width: 420px;"
            value-format="timestamp"
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
            v-model="categoryId"
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
            value="1"
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
          <el-image :src="cover" @click="attachmentDrawer = true"></el-image>
          <div class="aaa">
            <el-button type="success" plain @click="attachmentDrawer = true"
              >附件库</el-button
            >
            <el-button type="info" @click="uploadVisible = true"
              >上传附件</el-button
            >
          </div>
          <el-input v-model="cover" placeholder="可以输入封面图片地址">
          </el-input>
        </div>
        <!--缩略图 end-->
      </div>
      <!--内容区 end -->
      <div class="drawer-footer">
        <el-button type="warning">保存草稿</el-button>
        <el-button type="primary" @click="addArticle">发布</el-button>
      </div>
      <div>
        <el-drawer
          title="附件库"
          size="20%"
          :append-to-body="true"
          :before-close="handleClose"
          :visible.sync="attachmentDrawer"
          @open="attachmentDrawerOpen()"
        >
          <div class="attachment-drawer-con">
            <div class="search">
              <el-input
                style="width:70%"
                v-model="attachmentSearch"
                placeholder="请输入图片名称"
              ></el-input>
              <el-button type="primary" icon="el-icon-search">搜索</el-button>
            </div>
            <el-divider></el-divider>
            <div
              class="content"
              v-for="item in attachmentImages"
              :key="item.id"
            >
              <el-image
                fit="fit"
                style="height: 100%; width: 100%;"
                :src="item.path"
                @click="attachmentImageClick(item.path)"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                </div>
              </el-image>
            </div>
            <div class="page">
              <el-pagination
                background
                layout="prev, pager, next"
                :total="attachmentImageTotal"
              >
              </el-pagination>
            </div>

            <div class="drawer-footer">
              <el-button type="primary" @click="uploadVisible = true"
                >上传图片</el-button
              >
            </div>
          </div>
        </el-drawer>
      </div>
    </el-drawer>
    <!-- 侧边栏end   -->
    <!-- 文件上传弹框 start  -->
    <el-dialog title="文件上传" width="20%" :visible.sync="uploadVisible">
      <el-upload
        class="upload-demo"
        name="files"
        drag
        action="http://localhost:8081/admin/file/upload"
        multiple
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-dialog>
    <!-- 文件上传弹框 end   -->
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import api from "@/api/urls";
export default {
  data() {
    return {
      date: null,
      id: null,
      isComment: 1,
      isTop: 1,
      categoryOptions: [],
      categoryId: "",
      tagOptions: [],
      tag: "",
      summary: "",
      cover: "//i.loli.net/2019/05/05/5ccf007c0a01d.png",
      loading: false,
      dialogVisible: false, //控制抽屉
      attachmentDrawer: false,
      uploadVisible: false,
      uploadUrl: api.uploadFile,
      attachmentSearch: "",
      attachmentImages: [],
      attachmentImageTotal: 0
    };
  },
  components: {},
  created() {
    const art = this.$route.query.data;
    if (art !== undefined) {
      window.console.log(art);
      this.id = art.id;
      this.title = art.title;
      this.blog = art.content;
      this.date = art.createTime;
      this.isComment = art.disallowComment;
      this.isTop = art.isTop;
      this.tag = art.tagsId;
      this.categoryId = art.categoryId;
      this.summary = art.summary;
      this.cover = art.cover;
    }
  },
  methods: {
    ...mapActions(["setTitleAsyns", "SetBlogAsyns"]),
    ...mapMutations(["setTitle"]),
    handleClose(done) {
      done();
    },
    //标签的初始化方法
    initTag() {
      this.axios
        .get(api.tags)
        .then(response => {
          this.tagOptions = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //分类的初始化方法
    initCategory() {
      this.axios
        .get(api.categorys)
        .then(response => {
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
      if (this.categoryId.length <= 0) {
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
      const article = {
        id: this.id,
        title: this.title,
        createTime: this.date,
        publishTime: this.date,
        disallowComment: this.isComment,
        isTop: this.isTop,
        tag: this.tag,
        categoryId: this.categoryId,
        content: this.blog,
        summary: this.summary,
        cover: this.cover
      };
      this.axios
        .post(api.addArticle, article)
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
    },
    // 附件侧边栏的打开回调
    attachmentDrawerOpen() {
      this.axios
        .get(api.fileImages, {
          params: {
            current: 1,
            size: 8
          }
        })
        .then(response => {
          if (response.data.code === 200) {
            this.attachmentImages = response.data.data.data;
            this.attachmentImageTotal = response.data.data.total;
          }
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //附件图片的点击方法
    attachmentImageClick(path) {
      window.console.log(path);
      this.cover = path;
      this.attachmentDrawer = false;
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
.drawer-con {
  padding-bottom: 70px;
}
.drawer-footer {
  position: absolute;
  /*border: #42b983 solid 1px;*/
  right: 0;
  bottom: 0;
  width: 100%;
  height: 60px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  background: #ffffff;
  border-top: #dcdfe6 solid 1px;
  padding-right: 20px;
}
.attachment-drawer-con {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  align-items: center;
  height: 80%;
  align-content: flex-start;
  /*background: #42b983;*/
  .search {
    width: 100%;
    display: flex;
    justify-content: center;
    /*align-items: center;*/
  }
  .content {
    width: 45%;
    margin-top: 5px;
    height: 140px;
  }
  .page {
    width: 100%;
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
  }
}
</style>
