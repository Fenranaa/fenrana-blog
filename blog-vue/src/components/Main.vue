<template>
  <div class="main">
    <div class="cart" v-for="item in articles" :key="item.id">
      <div class="cart-left">
        <el-image
          style="width: 100%; height: 100%"
          :src="item.cover"
          fit="cover"
        ></el-image>
      </div>
      <div class="cart-right">
        <div class="title" @click="toArticlePage(item.id)">
          {{ item.title }}
        </div>
        <div class="center">
          {{ item.summary }}
        </div>
        <div class="tag">
          <el-tag
            size="small"
            type="info"
            v-for="tagItem in item.tags"
            :key="tagItem.id"
            >{{ tagItem.name }}
          </el-tag>
        </div>
        <div class="foot">
          <div class="foot-left">
            <div>
              <i class="iconfont el-icon-user"></i>
              <span>{{ item.author }}</span>
            </div>
            <div>
              <i class="iconfont el-icon-chat-dot-round"></i>
              <span>{{ item.visits }}</span>
            </div>
          </div>
          <div class="foot-right">
            <i class="iconfont el-icon-date"></i>
            <span>{{ getTimeByTimestamp(item.createTime) }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="page">
      <el-pagination background layout="prev, pager, next" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
// import Sidebar from "./Sidebar";
import { getRequest } from "../utils/request";
import { httpCodeValidate } from "../utils/HttpCodeValidate";

export default {
  name: "Main",
  created() {
    getRequest("/common/articles", { current: this.current }).then(res => {
      httpCodeValidate(res, () => {
        this.total = res.data.data.total;
        this.articles = res.data.data.articles;
      });
    });
  },
  components: {
    // Sidebar
  },
  methods: {
    getTimeByTimestamp(timestamp) {
      var date = new Date(timestamp + 8 * 3600 * 1000);
      return date
        .toJSON()
        .substr(0, 10)
        .replace("T", " ")
        .replace(/-/g, ".");
    },
    toArticlePage(id) {
      this.$router.push({
        path: "article/" + id
      });
    }
  },
  data() {
    return {
      articles: [],
      searchKey: "",
      current: 1,
      total: 0
    };
  }
};
</script>

<style scoped lang="scss">
@import "../assets/styles/common.scss";

.main {
  .cart {
    margin-top: 10px;
    height: 230px;
    /*background: #d3dce6;*/
    background-color: $mainBackgroundColor;
    display: flex;
    border-radius: 8px;
    justify-content: space-between;
    padding: 10px;

    .cart-left {
      border-radius: 10px;
      overflow: hidden;
      width: 40%;
      cursor: pointer;
    }

    .cart-right {
      width: 58%;
      display: flex;
      flex-direction: column;
      justify-content: space-between;

      .title {
        font-size: 18px;
        font-weight: bold;
        cursor: pointer;

        &:hover {
          color: #e67e22;
        }
      }

      .center {
        height: 38%;
        overflow: hidden;
        text-indent: 2em;
        display: -webkit-box;
        -webkit-line-clamp: 4;
        -webkit-box-orient: vertical;
      }

      .tag {
        /*background-color: darkkhaki;*/
        & > span:nth-child(n + 2) {
          margin-left: 10px;
        }
      }

      .foot {
        /*background-color: #ebebeb;*/
        display: flex;
        justify-content: space-between;
        align-items: center;

        .foot-left {
          display: flex;
          justify-content: center;
          align-items: center;

          & > div:nth-child(n + 2) {
            margin-left: 10px;
          }

          & span {
            margin-left: 5px;
            color: #959595;
            font-size: 14px;
          }
        }

        .foot-right {
          span {
            margin-left: 5px;
            /*color: rgb(136, 136, 136);*/
            font-size: 14px;
            color: #959595;
          }
        }
      }
    }
  }

  .page {
    // width: 100%;
    // margin: 15px auto;
  }
}

@media screen and (max-width: 768px) {
  .main {
    .cart {
      .cart-left {
        display: none;
      }

      .cart-right {
        width: 100%;
      }
    }
  }
}

@media screen and (min-width: 788px) and (max-width: 992px) {
  .main {
  }
}

@media screen and (min-width: 992px) and (max-width: 1200px) {
}

@media screen and (min-width: 1200px) {
}
</style>
