<template>
  <div class="comment">
    <div class="title">
      <i class="el-icon-chat-round"></i>
      <span>评论</span>
    </div>
    <div class="hide-comment" v-if="disallowComment === 0">
      博主没有开启评论
    </div>

    <div class="show-comment" v-else>
      <CommentEditor :articleId="articleId" :articleTitle="articleTitle" />
      <div class="comment-list">
        <div class="comment-list-item" v-for="item in comments" :key="item.id">
          <img
            src="http://cdn.v2ex.com/gravatar/b80c159f0b540fc458a3b7ed1577bac9?s=256&d=mm"
          />
          <div class="comment-item-main">
            <div class="header">
              <span class="header-name">{{ item.name }}</span>
              <span class="header-time">{{ formatTime(item.time) }}</span>
            </div>
            <div class="content">
              {{ item.content }}
            </div>
            <div class="contols">回复</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { httpCodeValidate } from "../utils/HttpCodeValidate";
import { getRequest } from "../utils/request";
import moment from "moment";
import CommentEditor from "./CommentEditor";
export default {
  data() {
    return {
      author: "",
      email: "",
      authorUrl: "",
      textarea: "",
      comments: []
    };
  },
  props: {
    disallowComment: { type: Number },
    articleId: { type: Number },
    articleTitle: { type: String }
  },
  created() {
    getRequest("common/comments/article-id/" + this.articleId).then(res => {
      httpCodeValidate(res, () => {
        // window.console.log(res);
        this.comments = res.data.data;
        // moment.locale("zh-cn");
        // var b = moment(1599053610885).fromNow();
        // window.console.log(b);
      });
    });
  },
  components: {
    CommentEditor
  },
  mounted() {
    // window.console.log(this.disallowComment);
  },
  methods: {
    formatTime(timestamp) {
      var a = moment(timestamp).format("YYYY-MM-DD HH:mm");
      return a;
    }
  },
  name: "Comment"
};
</script>

<style lang="scss" scoped>
@import "../assets/styles/common.scss";
.comment {
  margin-top: 20px;
  background: #f4f4f4;
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
  padding-bottom: 15px;
  .title {
    margin-left: 10px;
    padding-bottom: 5px;
    color: #287aed;
    font-weight: bold;
    font-size: 22px;
    height: 40px;
    line-height: 40px;
    span {
      margin-left: 10px;
      font-size: 18px;
    }
  }

  .hide-comment {
    height: 50px;
    line-height: 50px;
    padding-left: 20px;
  }

  .show-comment {
    .comment-list {
      margin-top: 10px;

      .comment-list-item {
        margin-top: 20px;
        border-bottom: 1px solid #287aed;
        display: flex;
        flex-direction: row;

        justify-content: space-around;
        img {
          width: 80px;
          height: 80px;
          border-radius: 50%;
        }

        .comment-item-main {
          width: 85%;
          .header {
            line-height: 1.5;
            height: 40px;
            line-height: 40px;
            .header-name {
              font-size: 22;
              font-weight: 900;
              height: 30px;
            }
            .header-time {
              margin-left: 15px;
              font-size: 13px;
              color: #667c99;
            }
          }
        }

        .content {
          padding: 5px;
          // min-height: 30px;
          line-height: 2;
          font-size: 14px;
          color: #111;
        }
        .contols {
          text-align: right;
          margin-bottom: 15px;
          cursor: pointer;
          color: #667c99;
          font-size: 14px;
          &:hover {
            color: #0084fe;
          }
        }
      }
    }
  }
}
</style>
