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
          <el-form-item label="标题" prop="title">
            <el-input
              v-model="formData.title"
              placeholder="请输入标题"
            ></el-input>
          </el-form-item>
          <el-form-item label="作者" prop="author">
            <el-input
              v-model="formData.author"
              placeholder="请输入作者"
            ></el-input>
          </el-form-item>
          <el-form-item label="国际标准书号" prop="isbn">
            <el-input
              v-model="formData.isbn"
              placeholder="请输入国际标准书号"
            ></el-input>
          </el-form-item>
          <el-form-item label="出版社" prop="publisher">
            <el-input
              v-model="formData.publisher"
              placeholder="请输入出版社"
            ></el-input>
          </el-form-item>
          <el-form-item label="出版日期" prop="publicationDate">
            <el-date-picker
              type="datetime"
              placeholder="选择日期"
              value-format="yyyy-MM-dd HH:mm:ss"
              v-model="formData.publicationDate"
              style="width: 100%;"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="类别">
            <el-select
              v-model="formData.category"
              placeholder="请选择类别"
            >
              <el-option
                v-for="item in statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input
              v-model="formData.price"
              type="number"
              placeholder="请输入价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="库存" prop="stockQuantity">
            <el-input
              v-model="formData.stockQuantity"
              type="number"
              placeholder="请输入库存"
            ></el-input>
          </el-form-item>
          <el-form-item label="书籍位置" prop="shelfLocation">
            <el-input placeholder="书籍位置" v-model="formData.shelfLocation">
            </el-input>
          </el-form-item>
          <el-form-item label="简短介绍" prop="description">
            <el-input placeholder="简短介绍" v-model="formData.description">
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
<script >
import {newBookInfo, updateBookInfo} from "@/api/bookinfo";

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
      dialogTitle: '新增图书信息',
      statusList:[{
        label: '小说',
        value: '1'
      },{
        label: '科幻',
        value: '2'
      },{
        label: '历史',
        value: '3'
      }],
      locations:[],
      suppliers:[],
      rules: {
        title: [
          { required: true, message: '请输入图书名称', trigger: 'blur' },
        ],
        author: [
          { required: true, message: '请输入书籍的作者', trigger: 'blur' },
        ],
        isbn: [
          { required: true, message: '请输入标准书号', trigger: 'blur' },
        ],
        publicationDate: [
          { required: true, message: '请输入标准日期', trigger: 'blur' },
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
    add(){
      newBookInfo(this.formData).then(res => {
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
      updateBookInfo(this.formData).then(res => {
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
