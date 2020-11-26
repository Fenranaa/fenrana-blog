<template>
  <div class="archives">
    <div class="archives-item" v-for="(items, index) in archives" :key="index">
      <div class="year">
        {{ items.year }}
      </div>
      <div
        class="item"
        @click="toArticlePage(item.id)"
        v-for="item in items.data"
        :key="item.id"
      >
        <div class="time">{{ formatTime(item.create_time) }}</div>
        <div class="title">{{ item.title }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import { httpCodeValidate } from "../utils/HttpCodeValidate";
import { getRequest } from "../utils/request";
import moment from "moment";
export default {
  name: "Archives",
  data() {
    return {
      archives: []
    };
  },
  created() {
    getRequest("http://localhost:8081/common/article/year").then(res => {
      httpCodeValidate(res, () => {
        this.archives = res.data.data;
      });
    });
  },
  methods: {
    toArticlePage(id) {
      this.$router.push({
        path: "article/" + id
      });
    },
    formatTime(timestamp) {
      var a = moment(timestamp).format("MM-DD");
      return a;
    }
  }
};
</script>

<style lang="scss" scoped>
.archives {
  margin-top: 10px;
  background: #fff;
  min-height: 100px;
  padding: 10px;
  border-radius: 20px;
  .year {
    font-size: 25px;
    font-style: normal;
    line-height: 2;
    font-weight: 900;
    color: #363636;
    border-bottom: 1px solid rgb(58, 56, 56);
  }

  .item {
    padding-left: 20px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    height: 40px;
    color: #333333;
    font-size: 16px;
    line-height: 1.7;
    word-break: break-all;
    cursor: pointer;
    &:hover {
      background: rgba(40, 122, 237, 0.1);
      border-radius: 10px;
    }
    font-family: "Varela Round", "Microsoft YaHei", "Source Sans Pro",
      "Helvetica Neue", Menlo, Monaco, monospace, "Lucida Console", sans-serif,
      Helvetica, "Hiragino Sans GB", "Hiragino Sans GB W3",
      Source Han Sans CN Regular, WenQuanYi Micro Hei, Arial, sans-serif;
    .time {
    }
    .title {
      margin-left: 10px;
    }
  }
}
</style>
