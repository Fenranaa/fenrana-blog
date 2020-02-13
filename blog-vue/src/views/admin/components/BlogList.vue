<template>
  <div class="content">
    <div class="search">
      <div>
        <span>关键词:</span>
        <el-input
          v-model="pageQuery.searchKey"
          placeholder="请输入内容"
          style="width: 200px;margin-left: 8px;"
        ></el-input>
      </div>
      <div class="search-item">
        <span>文章状体:</span>
        <el-select
          v-model="state"
          placeholder="请选择"
          style="width: 200px;margin-left: 8px;"
        >
          <el-option
            v-for="item in states"
            :key="item.id"
            :label="item.name"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </div>

      <!--分类选择 start-->
      <div class="search-item">
        <span>分类：</span>
        <el-select
          v-model="pageQuery.category"
          placeholder="请选择"
          style="width: 200px;margin-left: 8px;"
        >
          <el-option
            v-for="item in categoryOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
      <!--分类选择 end-->
      <div class="search-item">
        <el-button type="primary">筛选</el-button>
        <el-button type="info">重置</el-button>
      </div>
    </div>
    <div class="list">
      <div class="content">
        <el-table
          :data="tableData"
          stripe
          style="width: 100%"
          v-loading="loading"
        >
          <el-table-column prop="title" label="标题" width="150">
          </el-table-column>
          <el-table-column
            prop="state"
            label="状态"
            width="130"
            :formatter="formatterState"
          >
          </el-table-column>
          <el-table-column prop="category.name" label="分类" width="130">
          </el-table-column>
          <el-table-column prop="tag" label="标签">
            <!--            <el-tag type="success" disable-transitions>vim</el-tag>-->
            <template slot-scope="scope">
              <el-tag
                type="success"
                v-for="item in scope.row.tags"
                :key="item.id"
                disable-transitions
                >{{ item.name }}</el-tag
              >
            </template>
          </el-table-column>
          <el-table-column prop="visits" label="访问数"> </el-table-column>
          <el-table-column
            prop="createTime"
            label="发布时间"
            :formatter="formatterDate"
          ></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <template v-if="scope.row.state === '2'">
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                  >编辑</el-button
                >
                <el-button
                  size="mini"
                  type="info"
                  @click="articleRestore(scope.row)"
                  >还原</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="handleDelete(scope.$index, scope.row)"
                  >删除</el-button
                >
              </template>
              <template v-else>
                <el-button
                  size="mini"
                  @click="handleEdit(scope.$index, scope.row)"
                  >编辑</el-button
                >
                <el-button
                  size="mini"
                  type="info"
                  @click="articleRecycle(scope.row)"
                  >回收站</el-button
                >
                <el-button
                  size="mini"
                  type="danger"
                  @click="articleDelete(scope.row)"
                  >删除</el-button
                >
              </template>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          background
          :current-page.sync="pageQuery.current"
          @current-change="handleCurrentChange"
          :page-sizes="[10, 20, 30]"
          :page-size="100"
          layout="prev, pager, next,sizes"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/urls";
export default {
  name: "List",
  data() {
    return {
      state: null,
      states: [
        { id: 1, name: "已发布", value: 0 },
        { id: 2, name: "草稿", value: 1 },
        { id: 3, name: "回收站", value: 2 }
      ],
      categoryOptions: [],
      tableData: [],
      total: 0, //文章的总条数
      loading: true,
      pageQuery: {
        searchKey: "",
        category: "",
        current: 1,
        size: 10
      }
    };
  },
  created() {
    this.init();
    this.initData(this.pageQuery);
  },
  methods: {
    //分类的初始化方法
    initCategory() {
      this.axios
        .get(api.categorys)
        .then(response => {
          this.categoryOptions = response.data.data;
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    //数据的初始化方法
    initData(pageQuery) {
      this.axios
        .post(api.articles, pageQuery)
        .then(response => {
          if (response.data.code === 200) {
            this.tableData = response.data.data.data;
            this.total = response.data.data.total;
            this.loading = false;
          } else {
            this.$message({
              type: "info",
              message: "添加失败!"
            });
          }
        })
        .catch(error => {
          window.console.log(error);
        });
    },
    init() {
      this.initCategory();
    },
    //跳转到文章编辑界面
    handleEdit(index, row) {
      this.$router.push({
        path: "/admin/blog/write",
        query: {
          data: row
        }
      });
    },
    //文章彻底删除
    articleDelete(row) {
      this.sureMessage(row.title, "彻底删除?", api.articleDelete(row.id));
    },
    //文章加入回收站
    articleRecycle(row) {
      this.sureMessage(row.title, "加入回收站", api.articlesRecycle(row.id));
    },
    // 删除或加入回收站时的确认方法的封装
    sureMessage(title, meg, url) {
      this.$msgbox({
        title: "确认消息",
        message: "确定把标题为" + title + "的文章" + meg + "?",
        showCancelButton: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        beforeClose: (action, instance, done) => {
          if (action === "confirm") {
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = "执行中...";
            this.axios
              .get(url)
              .then(response => {
                if (response.data.code === 200) {
                  done();
                  instance.confirmButtonLoading = false;
                  this.initData(this.pageQuery);
                } else {
                  this.$message({
                    type: "info",
                    message: "添加失败!"
                  });
                }
              })
              .catch(error => {
                window.console.log(error);
              });
          } else {
            done();
          }
        }
      }).then(action => {
        this.$message({
          type: "info",
          message: "action: " + action
        });
      });
    },
    // 文章还原 回收站 -> 正常发布
    articleRestore(row) {
      this.sureMessage(row.title, "恢复正常吗", api.articleRestore(row.id));
    },
    //文章状态的计算
    formatterState(row) {
      if (row.state === "0") {
        return "正常发布";
      } else if (row.state === "1") {
        return "草稿";
      } else if (row.state === "2") {
        return "回收站";
      }
    },
    //日期的转换
    formatterDate(row) {
      const date = new Date(row.createTime);
      return date.toLocaleString();
    },
    handleSizeChange(val) {
      window.console.log(`每页 ${val} 条`);
    },
    // 下一页
    handleCurrentChange(val) {
      this.loading = true;
      this.pageQuery.current = val;
      this.initData(this.pageQuery);
    }
  }
};
</script>

<style scoped lang="less">
.content {
  height: 100%;
  .search {
    height: 5%;
    width: 1000%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    align-content: center;
    .search-item {
      margin-left: 40px;
    }
  }

  .list {
    margin-top: 15px;
    .content {
      /*height: 800px;*/
    }
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 10px;
    }
  }
}
</style>
