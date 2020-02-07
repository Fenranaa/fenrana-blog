<template>
  <h1>o仪表盘，展示各种界面</h1>
</template>

<script>
export default {
  data() {
    return {
      value: "#### how to use mavonEditor in nuxt.js",
      title: ""
    };
  },
  created() {
    this.$bus.$on("addBlog", () => {
      window.console.log("我是dash");
    });
  }
};
</script>
