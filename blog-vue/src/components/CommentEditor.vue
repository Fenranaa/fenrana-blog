<template>
  <div class="comment-editor">
    <div class="author-info">
      <el-input v-model="author" placeholder="*昵称"></el-input>
      <el-input v-model="email" placeholder="*邮箱"></el-input>
      <el-input v-model="authorUrl" placeholder="个人网址"></el-input>
    </div>
    <div class="comment-textarea">
      <el-input
        type="textarea"
        :rows="4"
        placeholder="请输入内容"
        v-model="textarea"
      >
      </el-input>
    </div>
    <el-alert
      v-if="showError"
      style="margin-top:10px;"
      :title="errorTitle"
      type="error"
      show-icon
    >
    </el-alert>
    <div class="comment-button">
      <el-button type="success" @click="submitComment()">提交</el-button>
    </div>
  </div>
</template>

<script>
import { httpCodeValidate } from "../utils/HttpCodeValidate";
import { postRequest } from "../utils/request";
export default {
  name: "CommentEditor",
  data() {
    return {
      textarea: "",
      author: "",
      email: "",
      authorUrl: "",
      showError: false,
      errorTitle: ""
    };
  },
  props: {
    articleId: { type: Number },
    articleTitle: { type: String }
  },
  created() {
    // window.console.log("子组件的id", this.articleId);
  },
  methods: {
    submitComment() {
      if (this.author === "") {
        this.errorTitle = "昵称不能为空";
        this.showError = true;
        return;
      }

      if (this.email === "") {
        this.errorTitle = "邮箱不能为空";
        this.showError = true;
        return;
      } else {
        if (!this.isEmail(this.email)) {
          this.errorTitle = "邮箱格式不正确";
          this.showError = true;
          return;
        }
      }

      if (this.authorUrl !== "") {
        if (!this.isUrl(this.authorUrl)) {
          this.errorTitle = "网址格式不正确";
          this.showError = true;
          return;
        }
      }

      if (this.textarea === "") {
        this.errorTitle = "评论内容不能为空！";
        this.showError = true;
        return;
      }
      this.errorTitle = "";
      this.showError = false;
      const comment = {
        articleId: this.articleId,
        email: this.email,
        name: this.author,
        url: this.authorUrl,
        content: this.textarea,
        articleTitle: this.articleTitle
      };

      postRequest("/common/comment", comment).then(res => {
        httpCodeValidate(res, () => {
          this.$message({
            type: "info",
            message: "评论通过，请等待管理员审核!"
          });
        });
      });
    },

    //校验网址是否正确
    isUrl(url) {
      var strRegex =
        "^((https|http|ftp|rtsp|mms)?://)" +
        "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" + //ftp的user@
        "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + // IP形式的URL- 199.194.52.184
        "|" + // 允许IP和DOMAIN（域名）
        "([0-9a-z_!~*'()-]+\\.)*" + // 域名- www.
        "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + // 二级域名
        "[a-z]{2,6})" + // first level domain- .com or .museum
        "(:[0-9]{1,4})?" + // 端口- :80
        "((/?)|" + // a slash isn't required if there is no file name
        "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
      var re = new RegExp(strRegex);
      //re.test()
      if (re.test(url)) {
        return true;
      } else {
        return false;
      }
    },
    //校验邮箱
    isEmail(email) {
      var strRegex = /^([a-zA-Z]|[0-9])(\w|\\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      var re = new RegExp(strRegex);
      if (re.test(email)) {
        return true;
      } else {
        return false;
      }
    }
  }
};
</script>

<style scoped lang="scss">
.comment-editor {
  border: 2px dashed #3b3737;

  padding: 10px;
  border-radius: 10px;
  &:hover {
    border: 2px dashed #3b83ee;
  }
  .author-info {
    .el-input {
      width: 32% !important;
    }
    display: flex;
    justify-content: space-between;
  }

  .comment-textarea {
    margin-top: 10px;
  }

  .comment-button {
    margin-top: 10px;
    text-align: right;
  }
}
</style>
