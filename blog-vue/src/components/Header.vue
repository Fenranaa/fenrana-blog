<template>
  <div class="header">
    <div class="header-wrapper">
      <div class="logo">
        <span>Fenrana</span>
        <span class="logo-text">个人技术博客</span>
      </div>
      <label for="toggle-nav">
        <i class="iconfont icon-zhankaicebianlan"></i>
      </label>
      <input id="toggle-nav" type="checkbox" />
      <div class="navbar">
        <div class="navbar-item">
          <i class="iconfont icon-shouye"></i>
          <span>首页</span>
        </div>
        <div class="navbar-item">
          <i class="iconfont icon-leimupinleifenleileibie"></i>
          <span>分类</span>
        </div>
        <div class="navbar-item">
          <i class="iconfont icon-shizhong"></i>
          <span>时光轴</span>
        </div>
        <div class="navbar-item">
          <i class="iconfont icon-lianjie"></i>
          <span>友链</span>
        </div>
        <div class="navbar-item">
          <i class="iconfont icon-gengduo"></i>
          <span>更多</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header"
};
</script>

<style scoped lang="scss">
@import "../assets/styles/common.scss";
.header {
  width: 100%;
  display: flex;
  justify-content: center;
  background: $headerBackgroundColor;
  .header-wrapper {
    display: flex;
    justify-content: space-between;
    .logo {
      line-height: $headerHeight;
      color: #1e90ff;
      font-size: 1.4rem;
      text-align: left;
      .logo-text {
        font-size: 0.6rem;
        color: #999;
        padding-left: 0.4rem;
      }
    }
    input {
      display: none;
    }
    .navbar {
      .navbar-item {
        cursor: pointer;
        height: 50px;
        padding: 0 10px;
        line-height: 50px;
        &:hover {
          background-color: #409eff;
          color: #fff;
          border-radius: 10px;
        }
        span {
          margin-left: 5px;
        }
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .header {
    .header-wrapper {
      width: 95%;
      align-items: center;
      flex-wrap: wrap;

      .logo {
        & + label {
          cursor: pointer;
          i {
            font-size: 20px;
          }
          & + input:checked {
            & + .navbar {
              display: block;
            }
          }
        }
      }
      .navbar {
        width: 100%;
        display: none;

        .navbar-item {
          justify-content: start;
        }
      }
    }
  }
}
@media screen and (min-width: 768px) {
  .header {
    justify-content: center;
    .header-wrapper {
      width: 80%;
      .logo {
        & + label {
          display: none;
        }
      }
      .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
    }
  }
}
</style>
