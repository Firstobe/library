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
          <el-form-item label="用户名" prop="userName">
            <el-input
              v-model="formData.userName"
              placeholder="请输入用户名"
            ></el-input>
          </el-form-item>
          <el-form-item label="手机号" prop="phoneNumber">
            <el-input
              v-model="formData.phoneNumber"
              placeholder="请输入手机号"
            ></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="formData.email"
              placeholder="请输入邮箱"
            ></el-input>
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
<script >
import {update} from "@/api/user";

export default {
  components: {
  },
  props: {
    isEdit: {
      type: Boolean,
      default: false,
    },
    formData: {
      type: Object,
      default: () => {},
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },

  data() {
    return {
      dialogTitle: this.isEdit ? '编辑信息' :'新增信息',

      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        phoneNumber: [
          { required: true, message: '请输入电话号', trigger: 'blur' },
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
        ],
      },

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
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    update(){
      update(this.formData).then(res => {
        if(res.code === 0){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.formData = {};
          this.$parent.showDialog = false;
          this.$parent.getUserInfo();
        }else{
          this.$message({
            message: '修改失败',
            type: 'error'
          });
        }
      })
    },


  },

}
</script>
<style scoped lang="scss">

</style>
