<template>
  <div>
    <template style="margin-top: 50px">
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
        prop="userName"
        label="用户名"
      >
      </el-table-column>
      <el-table-column
        prop="role"
        label="角色"
      >
      </el-table-column>
      <el-table-column
        prop="phoneNumber"
        label="电话"
      >
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
      >
      </el-table-column>
      <el-table-column
        prop="gender"
        label="性别"
      >
      </el-table-column>
    </el-table>

    <div style="margin-top: 10px;margin-left: 70%">
      <el-pagination
        background
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10,20,50,100]"
        :page-size="pagesize"
        layout="total, sizes, prev, pager, next, jumper"
      >
      </el-pagination>
    </div>
    <user-edit-dialog
      :form-data="choices"
      :is-edit="isEdit"
      :visible="showDialog">
    </user-edit-dialog>
  </div>
</template>
<script >
import userEditDialog from "@/views/userinfo/userEditDialog.vue";
import {getInfoByRole,deleteInfo} from "@/api/user";
export default {
  components: {userEditDialog,},
  data() {
    return {
      tableData: [],
      total:0,
      currentPage:1,
      pagesize:1,
      multipleSelection: [],
      showDialog: false,
      choices:{},
      isEdit:false,
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
    handleSizeChange(pagesize){
      this.pagesize = pagesize;
      let params = { pageNum: this.currentPage, pageSize: this.pagesize };
      getInfoByRole(params).then(res => {
        this.tableData= res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
      })
    },
    handleCurrentChange(currentPage){
      this.currentPage =  currentPage;
      let params = { pageNum: this.currentPage, pageSize: this.pagesize };
      getInfoByRole(params).then(res => {
        this.tableData= res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
      })
    },
    initData(){
      getInfoByRole().then(res => {
        this.tableData= res.data.records;
        this.total = res.data.total;
        console.log(this.tableData)
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
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteInfo({id:this.choices.id}).then(res => {
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
