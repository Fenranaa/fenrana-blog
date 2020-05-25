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
        <el-col :span="5"
          ><div class="grid-content">
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
        <el-col :span="3"
          ><div class="grid-content">
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
    <div class="content">
      <div class="content-item" v-for="item in files" :key="item.id">
        {{ item.name }}
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
        :page-sizes="[14, 28, 56]"
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
  </div>
</template>

<script>
import { getRequest, postRequest } from "../../../utils/request";
import { httpCodeValidate } from "../../../utils/HttpCodeValidate";

export default {
  name: "Attachment",
  data() {
    return {
      currentPage: 1,
      uploadVisible: false,
      fileSize: 14,
      searchKey: "",
      suffix: "",
      suffixOptions: [],
      saveLocation: "",
      saveLocationOptions: [],
      files: [],
      fileTotal: 0
    };
  },
  mounted() {
    this.initsuffixOptions();
    this.initFileSaveLocation();
    this.initAttachment();
  },
  methods: {
    //分页查询附件信息
    initAttachment() {
      let query = this.getQuery();
      postRequest("/admin/file/filesByQuery", query)
        .then(res => {
          httpCodeValidate(res, () => {
            this.currentPage = 1;
            this.files = res.data.data.records;
            this.fileTotal = res.data.data.total;
          });
          window.console.log(res);
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
      window.console.log(response);
      window.console.log(file);
      window.console.log(fileList);
    },
    //选择分页条数
    handleSizeChange(val) {
      this.fileSize = val;
    },
    //翻页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.initAttachment();
    },
    //把搜索条件置为空
    reset() {
      this.searchKey = "";
      this.suffix = "";
      this.saveLocation = "";
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
      width: 13%;
      background: #409eff;
      margin-top: 15px;
    }
    .content-item:not(:nth-child(7n)) {
      margin-right: calc(4% / 3);
    }
  }
  .page {
    margin-top: 30px;
    display: flex;
    justify-content: center;
  }
}
</style>
