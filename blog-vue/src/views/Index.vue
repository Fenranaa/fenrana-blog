<template>
  <div class="main-wrapper">
    <Header />
    <div>
      <router-view />
    </div>
    <div>333</div>
  </div>
</template>

<script>
import Header from "../components/Header";
import "element-ui/lib/theme-chalk/display.css";

export default {
  name: "Index",
  components: {
    Header
  }
};
</script>

<style scoped lang="scss">
.main-wrapper {
}
</style>
