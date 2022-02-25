<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户uid" prop="memberUid">
      <el-input v-model="dataForm.memberUid" placeholder="用户uid"></el-input>
    </el-form-item>
    <el-form-item label="回复某条评论的uid" prop="toUid">
      <el-input v-model="dataForm.toUid" placeholder="回复某条评论的uid"></el-input>
    </el-form-item>
    <el-form-item label="回复某个人的uid" prop="toMemberUid">
      <el-input v-model="dataForm.toMemberUid" placeholder="回复某个人的uid"></el-input>
    </el-form-item>
    <el-form-item label="评论内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="评论内容"></el-input>
    </el-form-item>
    <el-form-item label="状态" prop="status">
      <el-input v-model="dataForm.status" placeholder="状态"></el-input>
    </el-form-item>
    <el-form-item label="评论类型 0 = 评论 1 = 点赞" prop="type">
      <el-input v-model="dataForm.type" placeholder="评论类型 0 = 评论 1 = 点赞"></el-input>
    </el-form-item>
    <el-form-item label="一级评论uid" prop="firstCommentUid">
      <el-input v-model="dataForm.firstCommentUid" placeholder="一级评论uid"></el-input>
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
          toMemberUid: '',
          content: '',
          status: '',
          type: '',
          firstCommentUid: '',
          createdTime: ''
        },
        dataRule: {
          memberUid: [
            { required: true, message: '用户uid不能为空', trigger: 'blur' }
          ],
          toUid: [
            { required: true, message: '回复某条评论的uid不能为空', trigger: 'blur' }
          ],
          toMemberUid: [
            { required: true, message: '回复某个人的uid不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '评论内容不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '状态不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '评论类型 0 = 评论 1 = 点赞不能为空', trigger: 'blur' }
          ],
          firstCommentUid: [
            { required: true, message: '一级评论uid不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/comment/comment/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.memberUid = data.comment.memberUid
                this.dataForm.toUid = data.comment.toUid
                this.dataForm.toMemberUid = data.comment.toMemberUid
                this.dataForm.content = data.comment.content
                this.dataForm.status = data.comment.status
                this.dataForm.type = data.comment.type
                this.dataForm.firstCommentUid = data.comment.firstCommentUid
                this.dataForm.createdTime = data.comment.createdTime
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
              url: this.$http.adornUrl(`/comment/comment/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'memberUid': this.dataForm.memberUid,
                'toUid': this.dataForm.toUid,
                'toMemberUid': this.dataForm.toMemberUid,
                'content': this.dataForm.content,
                'status': this.dataForm.status,
                'type': this.dataForm.type,
                'firstCommentUid': this.dataForm.firstCommentUid,
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
