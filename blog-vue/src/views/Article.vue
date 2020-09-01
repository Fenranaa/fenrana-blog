<template>
  <div class="article">
    <Header />
    <div class="article-body">
      <div class="article-body-left" ref="body-left">
        <mavon-editor
          :defaultOpen="'preview'"
          :toolbarsFlag="false"
          :subfield="false"
          v-model="article.content"
          :ishljs="true"
        />
      </div>
      <div class="article-body-right">
        <ul class="directory">
          <li
            v-for="(item, index) in catalog"
            :key="index"
            class="directory-item"
            :class="index === directoryIndex ? 'active' : ''"
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
    <Comment />
    <div class="foot">
      <Foot />
    </div>
  </div>
</template>

<script>
var elementResizeDetectorMaker = require("element-resize-detector");
import Header from "../components/Header";
import { getRequest } from "../utils/request";
import { httpCodeValidate } from "../utils/HttpCodeValidate";
import Foot from "../components/Foot";
import Comment from "../components/Comment";

export default {
  name: "Article",
  data() {
    return {
      article: {},
      catalog: {},
      clientHeight: null, //屏幕高度
      directoryIndex: 0,
      hElement: []
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
    //添加定时器，组织scroll执行的次数过多
    debounce(func, wait, immediate) {
      var timeout;
      return function() {
        var context = this,
          args = arguments;
        var later = function() {
          timeout = null;
          if (!immediate) func.apply(context, args);
        };
        var callNow = immediate && !timeout;
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
        if (callNow) func.apply(context, args);
      };
    },
    isInViewPortOfTwo(el) {
      const viewPortHeight =
        window.innerHeight ||
        document.documentElement.clientHeight ||
        document.body.clientHeight;
      const top = el.getBoundingClientRect() && el.getBoundingClientRect().top;
      window.console.log("top", top);
      return top <= viewPortHeight;
    },
    //设置目录的央视
    setDirectoryStyle(left, top, height) {
      const articleBodyRightDom = document.querySelector(".article-body-right");
      if (left) {
        articleBodyRightDom.style.left = left + "px";
      }
      if (top) {
        articleBodyRightDom.style.top = top + "px";
      }
      if (height) {
        articleBodyRightDom.style.height = height + "px";
      }
    },
    generateDirectoryClassName(index) {
      return "directory-item-link-" + index;
    },
    generateDirectory() {
      var articleBody = document.querySelector(".v-show-content");

      const nodes = ["H1", "H2", "H3", "H4"];
      let titles = [];
      articleBody.childNodes.forEach((e, index) => {
        if (nodes.includes(e.nodeName)) {
          this.hElement.push(e);
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
      /**
       * 监听文章主体的宽度，当宽度变化时，修改文章的目录的left
       * */
      var erd = elementResizeDetectorMaker();
      erd.listenTo(
        document.getElementsByClassName("article-body-left"),
        element => {
          this.setDirectoryStyle(
            element.offsetLeft + element.offsetWidth + 20,
            null,
            null
          );
        }
      );
      //获取屏幕的高度
      if (this.clientHeight === null) {
        this.clientHeight =
          window.innerHeight ||
          document.documentElement.clientHeight ||
          document.body.clientHeight;
      }
      this.setDirectoryStyle(null, null, this.clientHeight * 0.6);

      //监听屏幕滚动
      this.listenScroll();
    },
    //监听屏幕滚动，随着屏幕的滚动，调节目录的位置，
    listenScroll() {
      // const articleBodyLeftDom = document.querySelector(".article-body-left");
      let articleBodyRightDom = document.querySelector(".article-body-right");
      let directoryDom = document.getElementsByClassName("directory-item");
      window.addEventListener(
        "scroll",
        this.debounce(() => {
          let scrollTop =
            document.documentElement.scrollTop || document.body.scrollTop;
          if (scrollTop > 60) {
            this.setDirectoryStyle(null, 5, this.clientHeight - 25);
          } else {
            this.setDirectoryStyle(null, 80, this.clientHeight - 100);
          }
          window.console.log("滚动方法");
          for (let i = 0; i < this.hElement.length; i++) {
            let a = this.hElement[i];
            let aTop = a.offsetTop;
            if (scrollTop > aTop) {
              this.directoryIndex = i;
            } else {
              break;
            }
          }
          if (
            directoryDom[this.directoryIndex].offsetTop >
            this.clientHeight - 50
          ) {
            articleBodyRightDom.scrollTop =
              directoryDom[this.directoryIndex].offsetTop - 100;
          }
        }, 250),
        true
      );
    }
  },
  components: {
    Header,
    Foot,
    Comment
  }
};
</script>

<style scoped lang="scss">
.article {
  .article-body {
    width: 70%;
    margin: 10px auto;
    &::after {
      content: "";
      clear: both;
      display: block;
    }
    .article-body-left {
      float: left;
      width: 70%;
      background-color: #ffffff;
    }

    .article-body-right {
      overflow: auto;
      float: right;
      width: 20%;
      &::-webkit-scrollbar {
        width: 4px;
      }
      &::-webkit-scrollbar-thumb {
        /*background-color: #42b983;*/
        background: rgba(0, 0, 0, 0.26);
      }
      position: fixed;
      .directory {
        padding: 0;
        margin: 0;
        .active {
          border-left: 2px solid red !important;
        }
        .directory-item {
          box-sizing: border-box;
          margin: 0;
          list-style: none;
          overflow: hidden;
          border-left: 2px solid #ac9b9b;
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

  .foot {
    // clear: both;
    // padding-top: 20px;
  }
}
</style>
