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
      prop="experimentName"
      label="实验名称"
      >
    </el-table-column>
    <el-table-column
      prop="experimentNumber"
      label="实验编号"
      >
    </el-table-column>
    <el-table-column
      prop="experimentContent"
      label="实验内容"
      >
    </el-table-column>
    <el-table-column
      prop="experimentRequirements"
      label="实验要求"
     >
    </el-table-column>
    <el-table-column
      label="实验时间"
      prop="experimentTime"
      >
<!--      <template slot-scope="scope">{{ scope.row.experimentRequirements }}</template>-->
    </el-table-column>
    <el-table-column
      prop="locationName"
      label="实验地点名称"
      >
    </el-table-column>
    <el-table-column
      prop="teacherId"
      label="实验老师姓名"
    >
    </el-table-column>
<!--    <el-table-column-->
<!--      prop="teacherPhone"-->
<!--      label="实验老师电话"-->
<!--     >-->
<!--    </el-table-column>-->
    <el-table-column
      prop="userName"
      label="实验人"
    >
    </el-table-column>
    <el-table-column
      prop="equipmentName"
      label="实验设备名称"
    >
    </el-table-column>
    <el-table-column
      prop="experimentCategory"
      label="实验类型"
    >
    </el-table-column>
  </el-table>

<div style="margin-top: 10px;margin-left: 80%">
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
    <lab-experiment-edit-dialog
      :form-data="choices"
      :is-edit="isEdit"
      :visible="showDialog">
    </lab-experiment-edit-dialog>
  </div>
</template>
<script >
import EquipmentEditDialog from "@/views/equipmentinfo/equipmentEditDialog.vue";
import {getLaboratoryInfo,deleteLabExperimentInfo} from "@/api/labExperimentInfo";
import LabExperimentEditDialog from "@/views/labexperimentinfo/labExperimentEditDialog.vue";
import {getLocationPage} from "@/api/location";

export default {
  components: {LabExperimentEditDialog, EquipmentEditDialog},
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
      getLaboratoryInfo(params).then(res => {
        this.tableData= res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
      })
    },
    handleCurrentChange(currentPage){
      this.currentPage =  currentPage;
      let params = { pageNum: this.currentPage, pageSize: this.pagesize };
      getLaboratoryInfo(params).then(res => {
        this.tableData= res.data.records;
        this.total = res.data.total;
      }).catch(err => {
        console.log(err)
      })
    },
    initData(){
      getLaboratoryInfo().then(res => {
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
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteLabExperimentInfo({id:this.choices.id}).then(res => {
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
