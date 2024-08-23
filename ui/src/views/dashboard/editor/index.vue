<template>
  <div >
<!--    <div class=" clearfix">-->
<!--      <pan-thumb :image="avatar" style="float: left">-->
<!--        角色:-->
<!--        <span v-for="item in roles" :key="item" class="pan-info-roles">{{ item }}</span>-->
<!--      </pan-thumb>-->
<!--      <github-corner style="position: absolute; top: 0px; border: 0; right: 0;" />-->
<!--      <div class="info-container">-->
<!--        <span class="display_name">{{ name }}</span>-->
<!--      </div>-->
<!--    </div>-->
    <el-descriptions class="margin-top" :column="3" :size="size">
      <template slot="extra">
        <el-button type="primary" size="small" @click="updateInfo">操作</el-button>
      </template>
      <el-descriptions-item label="用户名">{{ name }}</el-descriptions-item>
      <el-descriptions-item label="角色">{{ roles[0] }}</el-descriptions-item>
      <el-descriptions-item label="手机号">{{formData.phoneNumber}}</el-descriptions-item>
      <el-descriptions-item label="邮箱">{{formData.email}}</el-descriptions-item>
    </el-descriptions>
    <edit-dialog
      :visible.sync="showDialog"
      :form-data="formData"
      :is-edit="isEdit">

    </edit-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import GithubCorner from '@/components/GithubCorner'
import {getInfo} from "@/api/user";
import editDialog from "@/views/dashboard/editor/editDialog.vue";
export default {
  name: 'DashboardEditor',
  components: { PanThumb, GithubCorner ,editDialog},
  data() {
    return {
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3',
      size: '',
      showDialog:false,
      formData:{},
      isEdit:false
    }
  },
  methods: {
    updateInfo(){
      this.isEdit = true;
      this.showDialog = true;
      console.log(this.isEdit)
    },
    getUserInfo(){
      getInfo().then(res => {
        console.log(res)
        this.formData = res.data;
      })
    }
  },
  mounted() {
    this.getUserInfo();
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles',
      'userInfo'
    ])
  }
}
</script>

<style lang="scss" scoped>

</style>
