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
            ></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="passwordHash" v-if="!isEdit">
            <el-input
              type="password"
              v-model="formData.passwordHash"
            ></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role"  >
            <el-select
              v-model="formData.role"
            >
              <el-option
                v-for="item in roleOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="电话" prop="phoneNumber">
            <el-input
              v-model="formData.phoneNumber"
              type="number"
            ></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="formData.email"
            ></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="formData.gender">
              <el-radio label="男"></el-radio>
              <el-radio label="女"></el-radio>
            </el-radio-group>
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
import {register,getDESKey,update} from "@/api/user";
import {JSEncrypt} from 'jsencrypt';
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
      dialogTitle: '新增信息',
      isEdit: false,
      statusList:[{
        label: '正常',
        value: '1'
      },{
        label: '停用',
        value: '2'
      },{
        label: '维修',
        value: '3'
      }],
      roleOption: [{
        label: '管理员',
        value: 'admin'
      },{
        label: '客户',
        value: 'customer'
      }],
      classesGrade:[],
      department:[],
      teacherInfo:[],
      choiceFlag:false,
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        role: [
          { required: true, message: '请选择角色', trigger: 'blur' },
        ],
        passwordHash: [
          { required: true, message: '请输入密码', trigger: 'blur' },
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
          } else {
            _that.add();
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    async add() {
      console.log(this.formData)
      const encrypt = new JSEncrypt();
      const res = await getDESKey();
     let pubKey = res.data;
      console.log(pubKey)
      encrypt.setPublicKey(pubKey); // 设置公钥
      const encryptedPassword = encrypt.encrypt(this.formData.passwordHash); // 加密密码
      console.log(encryptedPassword)
      this.formData.passwordHash = encryptedPassword;
      console.log(this.formData)
      register(this.formData).then(res => {
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
    update(){
      update(this.formData).then(res => {
        if(res.code === 0){
          this.$message({
            message: '修改成功',
            type: 'success'
          });
          this.formData = {};
          this.$parent.showDialog = false;
          this.$parent.initData();
        }else{
          this.$message({
            message: '修改失败',
            type: 'error'
          });
        }
      })
    },


  },
  mounted() {
  }
}
</script>
<style scoped lang="scss">

</style>
