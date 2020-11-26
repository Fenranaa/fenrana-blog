<template>
  <div class="category">
    <div class="classify-bar">
      <span class="category-left">
        分类:
      </span>
      <div class="category-right">
        <div class="name active">
          全部
        </div>
        <div class="name" v-for="item in categorys" :key="item.categoryId">
          {{ item.name }}
        </div>
      </div>
    </div>
    <Main />
  </div>
</template>

<script>
import Main from "./Main";
export default {
  name: "Category",
  created() {
    this.init();
  },
  components: {
    Main
  },
  data() {
    return {
      categorys: []
    };
  },
  methods: {
    init() {
      var categorys = localStorage.getItem("categorys");
      this.categorys = JSON.parse(categorys);
    }
  }
};
</script>

<style lang="scss" scoped>
.category {
  margin-top: 10px;
  background: #fff;
  padding: 10px;
  border-radius: 20px;

  .classify-bar {
    display: flex;
    padding: 10px 0;
    font-size: 15px;
    border-bottom: 1px solid #eee;
    align-content: center;
    .category-left {
      color: #287aed;
      font-weight: bold;
      font-size: 16px;
      white-space: nowrap;
    }
    .category-right {
      margin-left: 10px;
      display: flex;
      align-content: center;
      flex-wrap: wrap;
      .name {
        font-size: 16px;
        margin-right: 4px;
        padding: 3px 8px;
        color: #777;
        white-space: nowrap;
        cursor: pointer;
        &:hover {
          color: #287aed;
        }
      }
      .active {
        color: #fff;
        background: #555;
        border-radius: 4px;
      }
    }
  }
}
</style>
