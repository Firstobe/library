<template>
  <div>
    <el-dialog
      :show-close="false"
      :title="isEdit ? '编辑信息' : '新增信息'"
      :visible.sync="visible"
      @close="cancelDialog()"
      :append-to-body="true"
    >
      <div class="edit">
        <el-form
          :model="formData"
          :rules="rules"
          ref="formData"
          label-width="120px"
        >
          <el-form-item label="实验名称" prop="experimentName">
            <el-input
              v-model="formData.experimentName"
              placeholder="请输入实验名称"
            ></el-input>
          </el-form-item>
          <el-form-item label="实验编号" prop="experimentNumber">
            <el-input
              v-model="formData.experimentNumber"
              placeholder="请输入实验编号"
            ></el-input>
          </el-form-item>
          <el-form-item label="实验内容" prop="experimentContent">
            <el-input
              type="textarea"
              v-model="formData.experimentContent"
              placeholder="请输入实验内容"
            ></el-input>
          </el-form-item>
          <el-form-item label="实验要求" prop="experimentRequirements">
            <el-input
              type="textarea"
              v-model="formData.experimentRequirements"
              placeholder="请输入实验要求"
            ></el-input>
          </el-form-item>
          <el-form-item label="实验日期" prop="experimentTime">
            <el-date-picker
              type="datetime"
              placeholder="选择日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="formData.experimentTime"
              style="width: 100%;"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="实验地址">
            <el-select
              v-model="formData.locationId"
            >
              <el-option
                v-for="item in locations"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="老师名称" prop="teacherId">
            <el-select
              v-model="formData.teacherId"
            >
              <el-option
                v-for="item in teacherInfo"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备名称">
            <el-select
              v-model="formData.equipmentId"
              placeholder="请选择设备名称"
            >
              <el-option
                v-for="item in equipmentNameList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="实验类型" prop="experimentCategory">
            <el-input placeholder="实验类型" v-model="formData.experimentCategory">
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="submitDialog()">保 存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {getSupplier} from "@/api/supplier";
import {getLocation} from "@/api/location";
import {getEquipmentInfo} from "@/api/equipmentinfo";
import {getAllTeacherInfo} from "@/api/user";
import {add, updateLabExperimentInfo} from "@/api/labExperimentInfo";

export default {
  components: {
    getSupplier,
    getLocation,
  },
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    formData: {
      type: Object,
      default: () => {
      },
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },

  data() {
    return {
      equipmentNameList: [],
      dialogTitle: '新增信息',
      locations: [],
      rules: {
        equipmentName: [
          {required: true, message: '请输入设备名称', trigger: 'blur'},
        ],
        modelNumber: [
          {required: true, message: '请输入设备型号', trigger: 'blur'},
        ],
        brand: [
          {required: true, message: '请输入设备品牌', trigger: 'blur'},
        ],
      },
      teacherInfo: [],
    }
  },
  methods: {
    cancelDialog() {
      if (this.$refs['formData'] !== undefined) {
        this.$refs['formData'].resetFields();
      }
      this.$parent.showDialog = false;
      this.$emit('update:showDialog', false);
    },
    submitDialog() {
      const _that = this;
      this.$refs['formData'].validate((valid) => {
        if (valid) {
          if (_that.isEdit) {
            _that.update();
          } else {
            _that.add();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    add() {
      console.log(this.equipmentNameList)
      this.formData.equipmentName = this.equipmentNameList.filter(item => item.value == this.formData.equipmentId)[0].label
      this.formData.teacherName = this.teacherInfo.filter(item => item.value == this.formData.teacherId)[0].label
      this.formData.locationName = this.locations.filter(item => item.value == this.formData.locationId)[0].label
      add(this.formData).then(res => {
        if(res.code === 0){
          this.$message({
            message: '新增成功',
            type: 'success'
          });
          this.$parent.showDialog = false;
          this.$parent.initData();
        }else{
          this.$message({
            message: '新增失败',
            type: 'error'
          });
        }
      })
    },
    update() {
      updateLabExperimentInfo(this.formData).then(res => {
        if (res.code === 0) {
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.formData = {};
          this.$parent.showDialog = false;
          this.$parent.initData();
        } else {
          this.$message({
            message: '修改失败',
            type: 'error'
          });
        }
      })
    },

    getLocationList() {
      getLocation().then(res => {
        res.data.forEach(item => {
          this.locations.push({
            label: item.locationName,
            value: item.id
          })
        })
      })
    },
    getTeacherInfoList() {
      getAllTeacherInfo().then(res => {
        res.data.forEach(item => {
          this.teacherInfo.push({
            label: item.userName,
            value: item.id
          })
        })
        console.log("laos", this.teacherInfo)
      })
    },
    getEquipmentInfo() {
      getEquipmentInfo().then(res => {
        this.equipmentList = res.data.records;
        res.data.records.forEach(item => {
          this.equipmentNameList.push({
            label: item.equipmentName,
            value: item.id
          })
        })
        console.log(this.equipmentNameList)
      })
    },
  },
  mounted() {
    this.getTeacherInfoList();
    this.getLocationList();
    this.getEquipmentInfo()
  }
}
</script>
<style scoped lang="scss">

</style>
