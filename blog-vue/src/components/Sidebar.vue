<template>
  <div class="sidebar">
    <div class="search">
      <el-input
        placeholder="请输入内容"
        prefix-icon="el-icon-search"
        v-model="searchKey"
      >
      </el-input>
    </div>
    <div class="info">
      <div class="info-avatar">
        <img
          src="https://pic2.zhimg.com/v2-beadf2aa093539e1a0a58da8dc672bb9_is.jpg"
        />
      </div>
      <div class="info-name">
        <span>Fenrana</span>
      </div>
      <div class="info-gushi">
        <span>听静夜之钟声，唤醒梦中之梦；</span>
        <span>观澄潭之月影，窥见身外之身。</span>
      </div>
      <div class="info-divider"></div>
      <div class="info-links">
        <div class="info-links-item">
          <i class="iconfont el-icon-message"></i>
          <span>邮箱</span>
        </div>
        <div class="info-links-item">
          <i class="iconfont icon-github"></i>
          <span>github</span>
        </div>
        <div class="info-links-item">
          <i class="iconfont icon-twiter"></i>
          <span>twiter</span>
        </div>
        <div class="info-links-item">
          <i class="iconfont icon-facebook"></i>
          <span>Facebook</span>
        </div>
        <div class="info-links-item">
          <i class="iconfont icon-qq"></i>
          <span>QQ</span>
        </div>
        <div class="info-links-item">
          <i class="iconfont icon-rss"></i>
          <span>Rss</span>
        </div>
      </div>
    </div>
    <div class="category">
      <div class="category-title">
        <i class="iconfont icon-folder-copy"></i>
        <span>分类</span>
      </div>
      <div class="category-content">
        <div class="category-content-item">
          <span>java</span>
          <span>(2)</span>
        </div>
        <div class="category-content-item">
          <span>java</span>
          <span>(2)</span>
        </div>
        <div class="category-content-item">
          <span>java</span>
          <span>(2)</span>
        </div>
        <div class="category-content-item">
          <span>java</span>
          <span>(2)</span>
        </div>
      </div>
    </div>
    <div class="tag">
      <div class="tag-title">
        <i class="iconfont icon-biaoqian1"></i>
        <span>标签</span>
      </div>
      <div class="tag-content">
        <el-tag type="info">Docker</el-tag>
        <el-tag type="info">spring boot</el-tag>
        <el-tag type="info">java</el-tag>
        <el-tag type="info">Linux</el-tag>
        <el-tag type="info">二叉树</el-tag>
        <el-tag type="info">设计模式</el-tag>
        <el-tag type="info">标签三</el-tag>
        <el-tag type="info">标签三</el-tag>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Sidebar",
  data() {
    return {
      searchKey: ""
    };
  }
};
</script>

<style scoped lang="scss">
@import "../assets/styles/common.scss";
.sidebar {
  margin-top: 10px;
  .search {
    background-color: #fff;
    border-radius: 5px;
  }
  .info {
    @include tag;
    .info-avatar {
      width: 80px;
      height: 80px;
      margin: 5px auto;
      border-radius: 50%;
      overflow: hidden;
      border: #409eff 1px solid;
      img {
        height: 100%;
        width: 100%;
      }
    }
    .info-name {
      text-align: center;
      color: #1e90ff;
      font-size: 16px;
    }
    .info-gushi {
      margin-top: 5px;
      font-size: 12px;
      text-align: center;
      display: flex;
      flex-direction: column;
    }
    .info-divider {
      margin: 10px auto;
      border-top: 1px solid #333333;
      width: 95%;
    }
    .info-links {
      /*margin-top: 10px;*/
      display: flex;
      flex-wrap: wrap;
      justify-content: space-around;
      align-items: center;
      & > div:nth-child(n + 3) {
        margin-top: 8px;
      }
      .info-links-item {
        background-color: #eee;
        height: 30px;
        line-height: 30px;
        cursor: pointer;
        width: 43%;
        border-radius: 5px;
        padding-left: 5px;
        &:hover {
          border-radius: 4px;
          background: #409eff;
        }
        span {
          margin-left: 5px;
        }
      }
    }
  }
  .category {
    @include tag;
    .category-title {
      @include tag-title;
    }
    .category-content {
      .category-content-item {
        height: 36px;
        line-height: 36px;
        font-size: 14px;
        font-weight: bold;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        color: rgba(51, 51, 51, 0.8);
        padding-left: 15px;
        padding-right: 15px;
        display: flex;
        justify-content: space-between;
        cursor: pointer;
        &:hover {
          border-left: 4px solid #287aed;
          background: rgba(40, 122, 237, 0.1);
        }
      }
    }
  }
  .tag {
    @include tag;
    .tag-title {
      @include tag-title;
    }
    .tag-content {
      /*padding-bottom: 10px;*/
      span {
        margin-left: 5px;
        margin-top: 5px;
        cursor: pointer;
        &:hover {
          background-color: #607f9a;
          color: #333333;
        }
      }
    }
  }
}
</style>
