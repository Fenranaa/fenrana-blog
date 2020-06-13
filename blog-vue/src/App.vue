<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="scss">
html,
body,
#app {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: #ebebeb;

  .el-drawer__body {
    overflow-y: auto !important;
  }
}

.el-drawer__body {
  overflow-y: auto !important;
}
</style>
