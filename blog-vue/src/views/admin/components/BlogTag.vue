<template>
  <div class="tag">
    <div class="search">
      <div class="search-left">
        <span>搜索：</span>
        <el-input v-model="searchKey" style="width: 300px;"></el-input>
      </div>
      <div class="search-right">
        <el-button type="success" @click="search">筛选</el-button>
        <el-button type="primary" @click="add()">添加</el-button>
      </div>
    </div>
    <div
      class="content"
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
    >
      <el-table :data="tagData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="200"></el-table-column>
        <el-table-column prop="name" label="标签的名称" width="200">
        </el-table-column>
        <el-table-column prop="description" label="标签的描述" width="400">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button @click="edit(scope.row)" type="primary">编辑</el-button>
            <el-button type="danger" @click="deletea(scope.row)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 修改弹出框 start-->
    <el-dialog
      :title="form.name === undefined ? '添加标签' : '修改标签'"
      :visible.sync="dialogFormVisible"
      width="500px"
    >
      <el-form :model="form" style="width:380px;margin: 0 auto;">
        <el-form-item label="标签名称">
          <el-input v-model="form.name" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="标签描述">
          <el-input v-model="form.description" style="width: 300px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitFrom" :loading="isUpdateLoading"
          >确 定
        </el-button>
      </div>
    </el-dialog>
    <!--    修改弹出窗end-->
  </div>
</template>

<script>
import { getRequest, postRequest } from "@/utils/request";
import { httpCodeValidate } from "../../../utils/HttpCodeValidate";

export default {
  data() {
    return {
      title: "",
      searchKey: "",
      tagData: [],
      form: {},
      dialogFormVisible: false,
      isUpdateLoading: false,
      loading: true
    };
  },
  created() {
    this.init();
  },
  methods: {
    edit(row) {
      this.dialogFormVisible = true;
      this.form = row;
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    //提交修改的分类
    submitFrom() {
      this.isUpdateLoading = true;
      var that = this;
      postRequest("/admin/addTag", {
        id: this.form.id,
        name: this.form.name,
        description: this.form.description
      }).then(response => {
        if (response.data.code === 200) {
          that.isUpdateLoading = false;
          this.init();
          that.dialogFormVisible = false;
          this.$message({
            type: "success",
            message: "添加成功!"
          });
        } else {
          this.$message({
            type: "info",
            message: "添加失败!"
          });
        }
      });
    },
    /*×数据初始化*/
    init() {
      const param = new URLSearchParams();
      if (this.searchKey !== "") {
        param.append("searchKey", this.searchKey);
      }
      getRequest("/admin/tags", param).then(response => {
        httpCodeValidate(response, () => {
          this.loading = false;
          this.tagData = response.data.data;
        });
      });
    },
    //删除分类名称
    deletea(row) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          getRequest("/admin/deleteTag/" + row.id).then(res => {
            httpCodeValidate(res, () => {
              this.$message({
                type: "info",
                message: "删除成功"
              });
              this.init();
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    //搜索
    search() {
      this.init();
    }
  }
};
</script>

<style scoped lang="less">
.tag {
  .search {
    display: flex;
    justify-content: flex-start;

    .search-right {
      margin-left: 15px;
    }
  }

  .content {
    margin-top: 20px;
  }
}
</style>
