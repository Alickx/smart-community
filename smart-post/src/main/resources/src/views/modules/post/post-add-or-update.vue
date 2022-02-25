<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="sectionUid">
      <el-input v-model="dataForm.sectionUid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="memberUid">
      <el-input v-model="dataForm.memberUid" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="title">
      <el-input v-model="dataForm.title" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="content">
      <el-input v-model="dataForm.content" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="文章状态 0 = 正常" prop="status">
      <el-input v-model="dataForm.status" placeholder="文章状态 0 = 正常"></el-input>
    </el-form-item>
    <el-form-item label="" prop="headImg">
      <el-input v-model="dataForm.headImg" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="collectCount">
      <el-input v-model="dataForm.collectCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="thumbCount">
      <el-input v-model="dataForm.thumbCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="0 = 不公布  1 = 公布" prop="isPublish">
      <el-input v-model="dataForm.isPublish" placeholder="0 = 不公布  1 = 公布"></el-input>
    </el-form-item>
    <el-form-item label="" prop="summary">
      <el-input v-model="dataForm.summary" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="clickCount">
      <el-input v-model="dataForm.clickCount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="updatedTime">
      <el-input v-model="dataForm.updatedTime" placeholder=""></el-input>
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
          sectionUid: '',
          memberUid: '',
          title: '',
          content: '',
          status: '',
          headImg: '',
          collectCount: '',
          thumbCount: '',
          isPublish: '',
          summary: '',
          clickCount: '',
          updatedTime: '',
          createdTime: ''
        },
        dataRule: {
          sectionUid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          memberUid: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          title: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '文章状态 0 = 正常不能为空', trigger: 'blur' }
          ],
          headImg: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          collectCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          thumbCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          isPublish: [
            { required: true, message: '0 = 不公布  1 = 公布不能为空', trigger: 'blur' }
          ],
          summary: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          clickCount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          updatedTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/post/post/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.sectionUid = data.post.sectionUid
                this.dataForm.memberUid = data.post.memberUid
                this.dataForm.title = data.post.title
                this.dataForm.content = data.post.content
                this.dataForm.status = data.post.status
                this.dataForm.headImg = data.post.headImg
                this.dataForm.collectCount = data.post.collectCount
                this.dataForm.thumbCount = data.post.thumbCount
                this.dataForm.isPublish = data.post.isPublish
                this.dataForm.summary = data.post.summary
                this.dataForm.clickCount = data.post.clickCount
                this.dataForm.updatedTime = data.post.updatedTime
                this.dataForm.createdTime = data.post.createdTime
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
              url: this.$http.adornUrl(`/post/post/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'sectionUid': this.dataForm.sectionUid,
                'memberUid': this.dataForm.memberUid,
                'title': this.dataForm.title,
                'content': this.dataForm.content,
                'status': this.dataForm.status,
                'headImg': this.dataForm.headImg,
                'collectCount': this.dataForm.collectCount,
                'thumbCount': this.dataForm.thumbCount,
                'isPublish': this.dataForm.isPublish,
                'summary': this.dataForm.summary,
                'clickCount': this.dataForm.clickCount,
                'updatedTime': this.dataForm.updatedTime,
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
