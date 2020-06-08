<template>
  <div class="log">
    <div class="search">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content">
            <span>浏览器:</span>
            <el-select
              v-model="browser"
              style="width: 80%;"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in browserOption"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <span>ip地址:</span>
            <el-input
              v-model="ipAddress"
              style="width: 80%"
              placeholder="请输入ip地址"
            ></el-input>
          </div>
        </el-col>
        <el-col :span="6"
          ><div class="grid-content">
            <span>平台:</span>
            <el-select
              v-model="device"
              style="width: 80%;"
              placeholder="请选择"
            >
              <el-option
                v-for="(item, index) in deviceOption"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select></div
        ></el-col>
        <el-col :span="6">
          <div class="grid-content">
            <span>角色:</span>
            <el-select v-model="role" style="width: 80%;" placeholder="请选择">
              <el-option
                v-for="(item, index) in roleOption"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content">
            <span>创建时间:</span>
            <el-date-picker
              v-model="createTime"
              type="datetimerange"
              style="width: 80%"
              value-format="timestamp"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </div>
        </el-col>
        <el-col :span="5">
          <div class="grid-content">
            <el-button @click="search" type="success">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>

            <el-popconfirm @onConfirm="deleteAll" title="确认清空全部数据吗？">
              <el-button type="danger" slot="reference">清空全部数据</el-button>
            </el-popconfirm>
          </div>
        </el-col>
      </el-row>
    </div>
    <!--主体内容 start-->
    <div class="content">
      <el-table
        :data="tableData"
        style="width: 100%"
        v-loading="loading"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
      >
        <el-table-column prop="id" label="ID" width="100"> </el-table-column>
        <el-table-column prop="method" label="操作方式" width="180">
        </el-table-column>
        <el-table-column prop="ip" label="ip地址" width="180">
        </el-table-column>
        <el-table-column prop="operation" label="操作描述"> </el-table-column>
        <el-table-column prop="username" label="操作用户"> </el-table-column>
        <el-table-column prop="device" label="设备"> </el-table-column>
        <el-table-column prop="browserName" label="浏览器"> </el-table-column>
        <el-table-column prop="browserVersion" label="浏览器版本">
        </el-table-column>

        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-popconfirm
              @onConfirm="deleteLog(scope.row.id)"
              title="确定删除吗？"
            >
              <el-button type="danger" slot="reference">删除</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 主题内容结束-->
    <!-- 分页 start -->
    <div class="page">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="sizes, prev, pager, next"
        :page-sizes="[10, 20, 30]"
        :current-page="currentPage"
        :total="dataTotal"
      >
      </el-pagination>
    </div>
    <!--  分页end  -->
  </div>
</template>

<script>
import { delRequest, getRequest } from "../../../utils/request";
import { httpCodeValidate } from "../../../utils/HttpCodeValidate";

export default {
  name: "Log",
  data() {
    return {
      loading: true,
      size: 10,
      currentPage: 1,
      dataTotal: 0,
      device: "",
      deviceOption: ["Windows", "iPhone", "Android", "MacOs"],
      browser: "",
      createTime: [],
      roleOption: ["Admin", "游客"],
      role: "",
      tableData: [],
      browserOption: [
        "Chrome",
        "MSEdge",
        "Firefox",
        "IEMobile",
        "Safari",
        "Opera"
      ],
      ipAddress: "",
      formInline: {},
      input: ""
    };
  },
  mounted() {
    this.initLog();
  },
  methods: {
    //刪除全部的数据
    deleteAll() {
      getRequest("admin/log/deleteAll").then(res => {
        httpCodeValidate(res, () => {
          this.$message({
            message: "删除成功",
            type: "success"
          });
          this.initLog();
        });
      });
    },
    //重置检索条件
    reset() {
      this.ipAddress = "";
      this.browser = "";
      this.device = "";
      this.role = "";
      this.createTime = [];
    },
    //删除一条记录
    deleteLog(id) {
      delRequest("admin/log/" + id).then(res => {
        httpCodeValidate(res, () => {
          this.$message({
            message: "删除成功",
            type: "success"
          });
          this.initLog();
        });
      });
    },
    search() {
      this.loading = true;
      this.initLog();
    },
    //选择分页条数
    handleSizeChange(val) {
      this.size = val;
      this.loading = true;
    },
    //翻页
    handleCurrentChange(val) {
      this.currentPage = val;
      this.loading = true;
    },
    //初始化参数
    initLog() {
      const param = this.buildQuery();
      const newParam = new URLSearchParams(param);
      getRequest("admin/log", newParam).then(res => {
        httpCodeValidate(res, () => {
          this.loading = false;
          this.tableData = res.data.data.records;
          this.total = res.data.data.total;
        });
      });
    },
    //构建请求参数
    buildQuery() {
      let query = {};
      if (this.ipAddress !== "") {
        query.ipAddress = this.ipAddress;
      }
      if (this.browser !== "") {
        query.browserName = this.browser;
      }
      if (this.device !== "") {
        query.device = this.device;
      }
      if (this.createTime.length > 0 && this.createTime !== "") {
        query.createTime = this.createTime[0] + "-" + this.createTime[1];
      }
      if (this.role !== "") {
        query.role = this.role;
      }
      query.size = this.size;
      query.current = this.currentPage;

      return query;
    }
  }
};
</script>

<style scoped lang="less">
.log {
  .search {
    padding: 10px;
    background: #42b983;
    border-radius: 5px;
    .el-row {
      margin-bottom: 20px;

      &:last-child {
        margin-bottom: 0;
      }
    }

    .el-col {
      border-radius: 4px;
      min-height: 36px;
    }

    .grid-content {
      display: flex;
      align-items: center;
      justify-content: space-around;
      border-radius: 4px;
      min-height: 36px;
    }
  }
  .content {
    border-radius: 4px;
    margin-top: 15px;
    min-height: 50px;
  }
  .page {
    margin-top: 30px;
    display: flex;
    justify-content: center;
  }
}
</style>
