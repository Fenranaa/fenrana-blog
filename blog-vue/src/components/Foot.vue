<template>
  <div class="foot">
    <span>豫ICP备18013735号</span>
    <span>©2020 Fenrana 版权所有</span>
  </div>
</template>

<script>
export default {
  name: "Foot"
};
</script>

<style lang="scss" scoped>
@import "../assets/styles/common.scss";
.foot {
  margin-top: 20px;
  height: $footHeight;
  background: $footBackground;
  color: $footFontColor;
  text-align: center;
  line-height: $footHeight;
  span:nth-child(n + 2) {
    border-left: red solid 1px;
    margin-left: 10px;
    padding-left: 10px;
  }
}
</style>
