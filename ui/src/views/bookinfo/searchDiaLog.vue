<template>
  <div>
    <el-dialog
      :show-close="false"
      title="搜索信息"
      :visible.sync="visible"
      @close="cancelDialog()"
      :append-to-body="true"
    >
      <div class="edit">
        <el-form
          :model="formData"
          ref="formData"
          label-width="120px"
        >
          <el-form-item label="标题" >
            <el-input
              v-model="formData.title"
              placeholder="请输入标题"
            ></el-input>
          </el-form-item>
          <el-form-item label="作者" >
            <el-input
              v-model="formData.author"
              placeholder="请输入作者"
            ></el-input>
          </el-form-item>
          <el-form-item label="国际标准书号" >
            <el-input
              v-model="formData.isbn"
              placeholder="请输入国际标准书号"
            ></el-input>
          </el-form-item>
          <el-form-item label="出版社">
            <el-input
              v-model="formData.publisher"
              placeholder="请输入出版社"
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelDialog()">取 消</el-button>
        <el-button type="primary" @click="submitDialog()">搜索</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script >
import {getBookInfoPage} from "@/api/bookinfo";

export default {
  components: {

  },
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
  },

  data() {
    return {
      dialogTitle: '新增图书信息',
      formData: {},
    }
  },
  methods: {

    cancelDialog() {
      if (this.$refs['formData'] !== undefined) {
        this.$refs['formData'].resetFields();
      }
      this.$parent.showSearch = false;
      this.$emit('update:showSearch', false);
    },
    submitDialog() {
      const _that = this;
      _that.search();
    },
    search(){
      getBookInfoPage(this.formData).then(res => {
        console.log(this.formData)
        let params ={
          title: this.formData.title
        }
        this.$parent.showSearch = false;
        this.$emit('update:showSearch', false);
        this.$parent.initData(this.formData);
      })
    },



  },
  mounted() {
  }
}
</script>
<style scoped lang="scss">

</style>
