<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="点赞用户的id" prop="memberUid">
      <el-input v-model="dataForm.memberUid" placeholder="点赞用户的id"></el-input>
    </el-form-item>
    <el-form-item label="点赞对象的uid" prop="toUid">
      <el-input v-model="dataForm.toUid" placeholder="点赞对象的uid"></el-input>
    </el-form-item>
    <el-form-item label="点赞的类型 0 = 文章 1 = 评论" prop="thumbType">
      <el-input v-model="dataForm.thumbType" placeholder="点赞的类型 0 = 文章 1 = 评论"></el-input>
    </el-form-item>
    <el-form-item label="" prop="createdTime">
      <el-input v-model="dataForm.createdTime" placeholder=""></el-input>
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
          toUid: '',
          thumbType: '',
          createdTime: ''
        },
        dataRule: {
          memberUid: [
            { required: true, message: '点赞用户的id不能为空', trigger: 'blur' }
          ],
          toUid: [
            { required: true, message: '点赞对象的uid不能为空', trigger: 'blur' }
          ],
          thumbType: [
            { required: true, message: '点赞的类型 0 = 文章 1 = 评论不能为空', trigger: 'blur' }
          ],
          createdTime: [
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
              url: this.$http.adornUrl(`/member/thumb/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberUid = data.thumb.memberUid
                this.dataForm.toUid = data.thumb.toUid
                this.dataForm.thumbType = data.thumb.thumbType
                this.dataForm.createdTime = data.thumb.createdTime
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
              url: this.$http.adornUrl(`/member/thumb/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'memberUid': this.dataForm.memberUid,
                'toUid': this.dataForm.toUid,
                'thumbType': this.dataForm.thumbType,
                'createdTime': this.dataForm.createdTime
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
