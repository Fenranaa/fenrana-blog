<template>
  <div class="article">
    <Header />
    <div class="article-body">
      <div class="article-body-left">
        <mavon-editor
          :defaultOpen="'preview'"
          :toolbarsFlag="false"
          :subfield="false"
          v-model="article.content"
        />
      </div>
      <div class="article-body-right">
        <ul class="directory">
          <li
            v-for="(item, index) in catalog"
            :key="index"
            class="directory-item"
          >
            <a
              :class="generateDirectoryClassName(item.level)"
              class="directory-item-link"
              >{{ item.title }}</a
            >
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header";
import { getRequest } from "../utils/request";
import { httpCodeValidate } from "../utils/HttpCodeValidate";

export default {
  name: "Article",
  data() {
    return {
      article: {},
      catalog: {}
    };
  },
  created() {
    const id = this.$route.params.id;
    getRequest("/common/article/" + id).then(res => {
      httpCodeValidate(res, () => {
        window.console.log("请求成功");
        this.article = res.data.data;
        setTimeout(() => {
          this.generateDirectory();
        }, 1000);
      });
    });
  },
  mounted() {
    this.$nextTick(() => {});
  },
  updated() {},
  methods: {
    generateDirectoryClassName(index) {
      return "directory-item-link-" + index;
    },
    generateDirectory() {
      window.console.log("开始渲染目录");
      var articleBody = document.querySelector(".v-show-content");
      window.console.log(articleBody);
      const nodes = ["H1", "H2", "H3", "H4"];
      let titles = [];
      articleBody.childNodes.forEach((e, index) => {
        if (nodes.includes(e.nodeName)) {
          const id = "header-" + index;
          e.setAttribute("id", id);
          var title = e.innerHTML + "";
          title = title.replace(/<.*>/, "");
          titles.push({
            id: id,
            title: title,
            level: Number(e.nodeName.substring(1, 2)),
            nodeName: e.nodeName
          });
        }
      });
      this.catalog = titles;
    }
  },
  components: {
    Header
    // Demo
  }
};
</script>

<style scoped lang="scss">
.article {
  .article-body {
    width: 70%;
    margin: 10px auto;
    background-color: #fff;
    /*display: flex;*/
    /*justify-content: space-between;*/

    .article-body-left {
      float: left;
      width: 70%;
    }

    .article-body-right {
        background-color: #42b983;
      float: right;
      width: 25%;
      position: relative;
      top: 0px;
      /*right: 100px;*/
        right: 0px;
      .directory {
        padding: 0;
        margin: 0;

        .directory-item {
          margin: 0;
          list-style: none;
          overflow: hidden;
          border-left: 2px solid #e8e8e8;

          .directory-item-link {
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            display: block;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            color: #595959;
            line-height: 30px;

            &:hover {
              color: #8c8c8c;
            }
          }

          .directory-item-link-1 {
            padding-left: 5px;
          }

          .directory-item-link-2 {
            padding-left: 10px;
          }

          .directory-item-link-3 {
            padding-left: 20px;
          }

          .directory-item-link-4 {
            padding-left: 20px;
          }

          .directory-item-link-5 {
            padding-left: 20px;
          }
        }
      }
    }
  }
}
</style>
