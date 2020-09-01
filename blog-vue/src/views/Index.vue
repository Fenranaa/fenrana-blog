<template>
  <div class="main-wrapper">
    <Header />
    <div>
      <router-view />
    </div>
    <Foot />
  </div>
</template>

<script>
import Header from "../components/Header";
import "element-ui/lib/theme-chalk/display.css";
import Foot from "../components/Foot";

export default {
  name: "Index",
  components: {
    Header,
    Foot
  }
};
</script>

<style scoped lang="scss">
.main-wrapper {
}
</style>
