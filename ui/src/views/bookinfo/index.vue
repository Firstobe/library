<template>
  <div>
    <template>
      <el-button type="primary" plain @click="search"
      >搜索<i class="el-icon-search"></i></el-button
      >
    </template>
    <template style="margin-top: 50px" v-if="hasAdminRole">
      <el-button type="primary" plain @click="addNew"
      >新 增</el-button
      >
      <el-button type="primary" plain @click="edit">修改</el-button>
      <el-button type="primary" plain @click="deleteOne"
      >删 除</el-button
      >
    </template>
  <el-table
    ref="multipleTable"
    :data="tableData"
    tooltip-effect="dark"
    style="width: 100%"
    @selection-change="handleSelectionChange">

    <el-table-column
      type="selection"
      width="55">
    </el-table-column>
    <el-table-column
      prop="title"
      label="标题"
      >
    </el-table-column>
    <el-table-column
      prop="isbn"
      label="国际标准书号"
      >
    </el-table-column>
    <el-table-column
      prop="publicationDate"
      label="日期"
      >
    </el-table-column>
    <el-table-column
      prop="publisher"
      label="出版社"
    >
    </el-table-column>
    <el-table-column
      prop="price"
      label="价格"
     >
    </el-table-column>
    <el-table-column
      prop="stockQuantity"
      label="库存数量"
      >
    </el-table-column>
    <el-table-column
      prop="shelfLocation"
      label="书籍位置"
    >
    </el-table-column>
    <el-table-column
      prop="status"
      label="状态"
      >
      <template slot-scope="scope">
        <span v-if="scope.row.status == 0">
          正常
        </span>
        <span v-if="scope.row.status == 1">
          停售
        </span>
      </template>
    </el-table-column>

  </el-table>

<div style="margin-top: 10px;margin-left: 80%">
  <el-pagination
    background
    layout="prev, pager, next"
    :total="total">
  </el-pagination>
</div>
    <book-info-edit-dialog
      :form-data="choices"
      :is-edit="isEdit"
      :visible="showDialog">
    </book-info-edit-dialog>
    <search-dia-log
    :visible="showSearch">
    </search-dia-log>
  </div>
</template>
<script >
import { mapGetters } from 'vuex'
import {deleteBookInfo, getBookInfoPage} from "@/api/bookinfo";
import bookInfoEditDialog from "@/views/bookinfo/bookInfoEditDialog.vue";
import searchDiaLog from "@/views/bookinfo/searchDiaLog.vue";
export default {
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles',
      'userInfo'
    ]),
    hasAdminRole() {
      return  this.roles[0].includes('admin')
    }
  },
  components: {bookInfoEditDialog, getBookInfoPage,searchDiaLog
    },
  data() {
    return {
      tableData: [],
      total:0,
      multipleSelection: [],
      showDialog: false,
      choices:{},
      isEdit:false,
      showSearch:false,
    }
  },
  mounted() {
    this.initData();
  },
  methods: {
    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;

    },
    initData(params){
      console.log(params)
      getBookInfoPage(params).then(res => {
        console.log(res)
        this.tableData= res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
      })
    },
    addNew(){
      this.showDialog = true;
      this.isEdit = false;
    },
    edit(){
      this.showDialog = true;
      this.choices = this.multipleSelection[0];
      this.isEdit = true;
    },
    deleteOne(){
      this.choices = this.multipleSelection[0];
      this.$confirm('此操作将删除该书籍信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBookInfo({id:this.choices.id}).then(res => {
          if(res.code === 0){
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            this.initData();
          }else{
            this.$message({
              type: 'error',
              message: '删除失败!'
            });
          }
        })
      });
    },
    search(){
      this.showSearch = true;
    }
  }
}
</script>
<style scoped lang="scss">
button{
  margin-top: 10px;
  margin-left: 80%;
  margin-bottom: 10px;
}
</style>
