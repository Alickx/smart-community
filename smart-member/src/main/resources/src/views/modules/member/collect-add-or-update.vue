<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户uid" prop="memberUid">
      <el-input v-model="dataForm.memberUid" placeholder="用户uid"></el-input>
    </el-form-item>
    <el-form-item label="文章uid" prop="postUid">
      <el-input v-model="dataForm.postUid" placeholder="文章uid"></el-input>
    </el-form-item>
    <el-form-item label="" prop="status">
      <el-input v-model="dataForm.status" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="createdTime">
      <el-input v-model="dataForm.createdTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updatedTime">
      <el-input v-model="dataForm.updatedTime" placeholder=""></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          uid: 0,
          memberUid: '',
          postUid: '',
          status: '',
          createdTime: '',
          updatedTime: ''
        },
        dataRule: {
          memberUid: [
            { required: true, message: '用户uid不能为空', trigger: 'blur' }
          ],
          postUid: [
            { required: true, message: '文章uid不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          createdTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updatedTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.uid = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.uid) {
            this.$http({
              url: this.$http.adornUrl(`/member/collect/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberUid = data.collect.memberUid
                this.dataForm.postUid = data.collect.postUid
                this.dataForm.status = data.collect.status
                this.dataForm.createdTime = data.collect.createdTime
                this.dataForm.updatedTime = data.collect.updatedTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/member/collect/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'memberUid': this.dataForm.memberUid,
                'postUid': this.dataForm.postUid,
                'status': this.dataForm.status,
                'createdTime': this.dataForm.createdTime,
                'updatedTime': this.dataForm.updatedTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
