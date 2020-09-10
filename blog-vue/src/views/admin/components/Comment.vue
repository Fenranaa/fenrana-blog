<template>
  <div class="comment">
    <div class="search">
      <div>
        <span>关键词:</span>
        <el-input
          v-model="searchKey"
          placeholder="请输入内容"
          style="width: 200px;margin-left: 8px;"
          size="small"
        ></el-input>
      </div>
      <div class="search-item">
        <span>评论状态:</span>
        <el-select
          v-model="state"
          placeholder="请选择"
          style="width: 200px;margin-left: 8px;"
          size="small"
        >
          <el-option :value="0" label="未审核">未审核 </el-option>
          <el-option :value="1" label="已发布">已发布 </el-option>
          <el-option :value="2" label="回收站">回收站 </el-option>
        </el-select>
      </div>

      <div class="search-item">
        <el-button type="primary" size="small" @click="search()"
          >筛选</el-button
        >
        <el-button type="info" size="small" @click="reset()">重置</el-button>
      </div>
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
        <el-table-column prop="id" label="ID" width="60"> </el-table-column>
        <el-table-column prop="name" label="昵称" width="120">
        </el-table-column>
        <el-table-column prop="content" label="内容" width="500">
        </el-table-column>
        <el-table-column
          prop="status"
          width="100"
          label="文章状态"
          :formatter="formatterState"
        >
        </el-table-column>
        <el-table-column prop="articleTitle" width="140" label="评论文章">
        </el-table-column>
        <el-table-column
          prop="time"
          label="日期"
          width="180"
          :formatter="formatterDate"
        >
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <template v-if="scope.row.status === 0">
              <el-popconfirm
                @onConfirm="updateCommentStatus(scope.row.id, 1)"
                title="审核通过评论?"
              >
                <el-button size="mini" slot="reference">通过</el-button>
              </el-popconfirm>
              <el-popconfirm
                style="margin-left: 8px;"
                @onConfirm="deleteCommentById(scope.row.id)"
                title="删除不可恢复,确定删除吗？"
              >
                <el-button size="mini" type="danger" slot="reference"
                  >删除</el-button
                >
              </el-popconfirm>
            </template>
            <template v-if="scope.row.status === 1">
              <el-button
                size="mini"
                @click="handleEdit(scope.$index, scope.row)"
                >回复</el-button
              >
              <el-popconfirm
                style="margin-left: 8px;"
                @onConfirm="updateCommentStatus(scope.row.id, 2)"
                title="将文章加入回收站?"
              >
                <el-button size="mini" slot="reference">回收站</el-button>
              </el-popconfirm>
            </template>

            <template v-if="scope.row.status === 2">
              <el-popconfirm
                @onConfirm="updateCommentStatus(scope.row.id, 1)"
                title="将文章还原?"
              >
                <el-button size="mini" slot="reference">还原</el-button>
              </el-popconfirm>

              <el-popconfirm
                style="margin-left: 8px;"
                @onConfirm="deleteCommentById(scope.row.id)"
                title="删除不可恢复,确定删除吗？"
              >
                <el-button size="mini" type="danger" slot="reference"
                  >删除</el-button
                >
              </el-popconfirm>
            </template>
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
import { getRequest, delRequest, putRequest } from "../../../utils/request";
import { httpCodeValidate } from "../../../utils/HttpCodeValidate";
export default {
  name: "Comment",
  data() {
    return {
      searchKey: "",
      state: "",
      currentPage: 1,
      dataTotal: 0,
      tableData: [],
      loading: true,
      size: 10
    };
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      const params = new URLSearchParams();
      params.append("curent", this.currentPage);
      params.append("size", this.size);
      if (this.searchKey !== "") {
        params.append("searchKey", this.searchKey);
      }
      if (this.state !== "") {
        params.append("status", this.state);
      }
      getRequest("admin/comments", params).then(res => {
        httpCodeValidate(res, () => {
          this.tableData = res.data.data.comments;
          this.loading = false;
        });
      });
    },
    handleSizeChange(val) {
      this.size = val;
    },
    // 下一页
    handleCurrentChange(val) {
      this.loading = true;
      this.pageQuery.current = val;
      this.initData(this.pageQuery);
    },
    //日期的转换
    formatterDate(row) {
      const date = new Date(row.time);
      return date.toLocaleString();
    },
    //文章状态的计算
    formatterState(row) {
      if (row.status === 0) {
        return "未审核";
      } else if (row.status === 1) {
        return "已发布";
      } else if (row.status === 2) {
        return "回收站";
      }
    },
    //根据id删除评论
    deleteCommentById(id) {
      delRequest("/admin/comment/" + id).then(res => {
        httpCodeValidate(res, () => {
          this.init();
          this.$message({
            message: "删除成功",
            type: "success"
          });
        });
      });
    },
    //通过id
    updateCommentStatus(id, status) {
      putRequest("admin/comment/" + id + "/status/" + status).then(res => {
        httpCodeValidate(res, () => {
          this.init();
          this.$message({
            message: "操作成功",
            type: "success"
          });
        });
      });
    },
    //筛选方法
    search() {
      this.init();
    },
    //重置方法
    reset() {
      this.searchKey = "";
      this.state = "";
      this.currentPage = 1;
      this.loading = true;
      this.init();
    }
  }
};
</script>

<style scoped lang="scss">
.comment {
  .search {
    height: 5%;
    width: 1000%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    .search-item {
      margin-left: 40px;
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
