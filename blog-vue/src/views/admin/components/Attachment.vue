<template>
  <div class="attachment">
    <!-- 搜索框 start   -->
    <div class="search">
      <el-row type="flex">
        <el-col :span="4">
          <div class="grid-content">
            <span>关键词：</span>
            <el-input v-model="searchKey" style="width: 70%;"></el-input>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content">
            <span>文件类型：</span>
            <el-select v-model="suffix" placeholder="请选择">
              <el-option
                v-for="(item, index) in suffixOptions"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content">
            <span>储存位置：</span>
            <el-select v-model="saveLocation" placeholder="请选择">
              <el-option
                v-for="(item, index) in saveLocationOptions"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="3">
          <div class="grid-content">
            <el-button @click="search" type="primary" plain>搜索</el-button>
            <el-button @click="reset" plain>重置</el-button>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="8">
          <div class="grid-content">
            <el-button @click="uploadFile" type="primary" icon="el-icon-upload">
              上传
            </el-button>
            <el-button type="primary" icon="el-icon-edit-outline">
              批量编辑
            </el-button>
          </div>
        </el-col>
      </el-row>
    </div>
    <!-- 搜索框 end  -->
    <!-- 主体内容 start  -->
    <div
      class="content"
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
    >
      <div class="content-item" v-for="(item, index) in files" :key="item.id">
        <div
          class="top"
          @mouseenter="infoButtonOver(index)"
          @mouseleave="infoButtonMouseout"
          v-if="!item.mediaType.search('image')"
          :style="{ backgroundImage: 'url(' + getImgUrl(item.path) + ')' }"
        >
          <div class="info" v-if="infoButtonVisible === index">
            <div>
              <el-button type="info" @click="openImage(item.path)"
                >查看大图</el-button
              >
              <el-button @click="openFileInfo(item)" type="info"
                >文件信息</el-button
              >
            </div>
          </div>
        </div>
        <!--不是图片的文件 start-->
        <div
          class="top"
          @mouseenter="infoButtonOver(index)"
          @mouseleave="infoButtonMouseout"
          v-else
          :style="{ backgroundImage: 'url(' + bgImage1() + ')' }"
        >
          <div class="info" v-if="infoButtonVisible === index">
            <div>
              <el-button @click="openFileInfo(item)" type="info"
                >文件信息</el-button
              >
            </div>
          </div>
        </div>
        <!--不是图片的文件 end-->
        <div class="bottom">
          {{ item.name }}
        </div>
      </div>
    </div>
    <!--  主体内容end  -->
    <!-- 分页 start -->
    <div class="page">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="sizes, prev, pager, next"
        :page-sizes="[12, 24]"
        :current-page="currentPage"
        :total="fileTotal"
      >
      </el-pagination>
    </div>
    <!--  分页end  -->
    <!-- 文件上传弹框 start  -->
    <el-dialog title="文件上传" width="20%" :visible.sync="uploadVisible">
      <el-upload
        class="upload-demo"
        name="files"
        :on-success="uploadSuccess"
        drag
        action="http://localhost:8081/admin/file/upload"
        multiple
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
    </el-dialog>
    <!-- 文件上传弹框 end   -->
    <!--文件详情 start-->
    <el-dialog
      width="25%"
      title="文件详情"
      :visible.sync="dialogFileInfoVisible"
    >
      <div class="file-info">
        <div class="file-info-item">
          <span>附件名：</span>
          <span class="file-info-item-right">{{ fileInfo.name }}</span>
        </div>
        <div class="file-info-item">
          <span>文件类型：</span>
          <span class="file-info-item-right"> {{ fileInfo.mediaType }}</span>
        </div>
        <div class="file-info-item">
          <span>文件后缀：</span>
          <span class="file-info-item-right">{{ fileInfo.suffix }}</span>
        </div>
        <div class="file-info-item">
          <span>文件大小：</span>
          <span class="file-info-item-right">{{ fileInfo.size }}</span>
        </div>
        <div class="file-info-item">
          <span>保存位置：</span>
          <span class="file-info-item-right">{{ fileInfo.saveLocation }}</span>
        </div>
        <div class="file-info-item">
          <span>上传日期：</span>
          <span class="file-info-item-right">{{ fileInfo.createTime }}</span>
        </div>
        <div class="file-info-image">
          <div>
            普通链接：
            <i class="el-icon-copy-document" style="cursor: pointer"></i>
          </div>
          <span>{{ getImgUrl(fileInfo.path) }}</span>
        </div>
        <div class="file-info-image" v-if="isShowImageMarkdown">
          <div>
            MarkDown链接：
            <i class="el-icon-copy-document" style="cursor: pointer"></i>
          </div>
          <span>{{ getMarkDownImgUrl(fileInfo.name, fileInfo.path) }}</span>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="deleteFile()">删除文件</el-button>
        <el-button @click="dialogFileInfoVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
    <!-- 文件详情 end-->
    <!--查看大图 start-->
    <el-dialog
      top="5vh"
      width="80%"
      title="图片"
      :fullscreen="true"
      :visible.sync="dialogImageVisible"
    >
      <div style="width: 100%;height: 100%;text-align: center;overflow: hidden">
        <img :src="openImageSrc" />
      </div>
    </el-dialog>
    <!--查看大图 end-->
  </div>
</template>

<script>
import { delRequest, getRequest, postRequest } from "../../../utils/request";
import { httpCodeValidate } from "../../../utils/HttpCodeValidate";
import { baseUrl } from "../../../utils/base";
import bgImage from "@/assets/images/zbyl.png";

export default {
  name: "Attachment",
  data() {
    return {
      isShowImageMarkdown: false,
      openImageSrc: "",
      dialogImageVisible: false,
      dialogFileInfoVisible: false,
      infoButtonVisible: -1,
      loading: true,
      currentPage: 1,
      uploadVisible: false,
      fileSize: 12,
      searchKey: "",
      suffix: "",
      suffixOptions: [],
      saveLocation: "",
      saveLocationOptions: [],
      files: [],
      fileTotal: 0,
      fileInfo: {}
    };
  },
  mounted() {
    this.initsuffixOptions();
    this.initFileSaveLocation();
    this.initAttachment();
  },
  methods: {
    //打开文件详情页的弹窗
    openFileInfo(item) {
      this.dialogFileInfoVisible = true;
      this.isShowImageMarkdown = false;
      this.fileInfo = item;
      this.isImageMarkdown(this.fileInfo.mediaType);
    },
    //分页查询附件信息
    initAttachment() {
      let query = this.getQuery();
      postRequest("/admin/file/filesByQuery", query)
        .then(res => {
          httpCodeValidate(res, () => {
            this.currentPage = 1;
            this.loading = false;
            this.files = res.data.data.records;
            this.fileTotal = res.data.data.total;
          });
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //获取查询参数
    getQuery() {
      let query = {};
      if (this.searchKey !== "") {
        query.searchKey = this.searchKey;
      }
      if (this.saveLocation !== "") {
        query.saveLocation = this.saveLocation;
      }
      if (this.suffix !== null) {
        query.suffix = this.suffix;
      }
      query.size = this.fileSize;
      query.current = this.currentPage;
      return query;
    },
    //搜索按钮
    search() {
      this.loading = true;
      this.initAttachment();
    },
    //初始化文件储存位置
    initFileSaveLocation() {
      getRequest("/admin/file/getFileSaveLocation").then(res => {
        httpCodeValidate(res, () => {
          this.saveLocationOptions = res.data.data;
        });
      });
    },
    //初始化文件类型
    initsuffixOptions() {
      getRequest("/admin/file/suffix").then(res => {
        httpCodeValidate(res, () => {
          this.suffixOptions = res.data.data;
        });
      });
    },
    //点击文件上传按钮
    uploadFile() {
      this.uploadVisible = true;
    },
    //文件上传成功的回调
    uploadSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.initAttachment();
      }
      window.console.log(response);
      window.console.log(file);
      window.console.log(fileList);
    },
    //选择分页条数
    handleSizeChange(val) {
      this.fileSize = val;
      this.loading = true;
      this.initAttachment();
    },
    //翻页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.loading = true;
      this.initAttachment();
    },
    //把搜索条件置为空
    reset() {
      this.searchKey = "";
      this.suffix = "";
      this.saveLocation = "";
    },
    getImgUrl(url) {
      return baseUrl + url;
    },
    //获取markdown格式的链接
    getMarkDownImgUrl(name, url) {
      return "![" + name + "](" + baseUrl + url + ")";
    },
    //鼠标经过图片
    infoButtonOver(index) {
      this.infoButtonVisible = index;
    },
    // 鼠标移出
    infoButtonMouseout() {
      this.infoButtonVisible = -1;
    },
    //查看大图
    openImage(url) {
      this.openImageSrc = this.getImgUrl(url);
      this.dialogImageVisible = true;
    },
    //加载不支持预览的文件的背景图片
    bgImage1() {
      return bgImage;
    },
    //判断是否要显示图片的markdown链接
    isImageMarkdown(mediaType) {
      if (!mediaType.search("image")) {
        this.isShowImageMarkdown = true;
      }
    },
    //删除文件
    deleteFile() {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          const param = new URLSearchParams();
          param.append("path", this.fileInfo.path);
          delRequest("/admin/file/" + this.fileInfo.id, param).then(res => {
            window.console.log(res);
            httpCodeValidate(res, () => {
              this.dialogFileInfoVisible = false;
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.initAttachment();
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    }
  }
};
</script>

<style scoped lang="less">
.attachment {
  .search {
    height: 100%;
    padding: 5px;
    background: #ffffff;

    .el-row {
      margin-bottom: 20px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }

  .content {
    height: 100%;
    padding: 5px;
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;

    .content-item {
      height: 220px;
      width: 15.293%;
      margin-top: 15px;
      display: flex;
      background: #b3c0d1;
      padding: 5px;
      flex-direction: column;
      justify-content: space-between;
      border-radius: 5px;

      .top {
        cursor: pointer;
        height: 85%;
        /*backgroundimage: url("../../../assets/images/zbyl.png");*/
        background-repeat: round;
        .info {
          background-color: rgba(51, 51, 51, 0.5);
          height: 100%;
          width: 100%;
          display: flex;
          justify-content: center; /* 水平居中 */
          align-items: center;
        }
      }

      .bottom {
        height: 10%;
      }
    }

    .content-item:not(:nth-child(7n)) {
      margin-right: calc(4% / 5);
    }
  }

  .page {
    margin-top: 30px;
    display: flex;
    justify-content: center;
  }
  .file-info {
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    .file-info-item {
      height: 40px;
      line-height: 40px;
      /*background: red;*/
      margin-bottom: 6px;
      border-bottom: #333333 solid 1px;
      .file-info-item-right {
        margin-left: 5px;
      }
    }
    .file-info-image {
      margin-bottom: 6px;
      padding: 5px;
      border-bottom: #333333 solid 1px;
      span {
        margin-top: 6px;
        display: block;
      }
    }
  }
}
</style>
