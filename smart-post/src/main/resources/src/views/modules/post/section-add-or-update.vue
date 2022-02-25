<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="板块名称" prop="name">
      <el-input v-model="dataForm.name" placeholder="板块名称"></el-input>
    </el-form-item>
    <el-form-item label="板块图标" prop="icon">
      <el-input v-model="dataForm.icon" placeholder="板块图标"></el-input>
    </el-form-item>
    <el-form-item label="板块链接" prop="url">
      <el-input v-model="dataForm.url" placeholder="板块链接"></el-input>
    </el-form-item>
    <el-form-item label="板块介绍" prop="intro">
      <el-input v-model="dataForm.intro" placeholder="板块介绍"></el-input>
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
          name: '',
          icon: '',
          url: '',
          intro: '',
          updatedTime: '',
          createdTime: ''
        },
        dataRule: {
          name: [
            { required: true, message: '板块名称不能为空', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '板块图标不能为空', trigger: 'blur' }
          ],
          url: [
            { required: true, message: '板块链接不能为空', trigger: 'blur' }
          ],
          intro: [
            { required: true, message: '板块介绍不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/post/section/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.name = data.section.name
                this.dataForm.icon = data.section.icon
                this.dataForm.url = data.section.url
                this.dataForm.intro = data.section.intro
                this.dataForm.updatedTime = data.section.updatedTime
                this.dataForm.createdTime = data.section.createdTime
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
              url: this.$http.adornUrl(`/post/section/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'name': this.dataForm.name,
                'icon': this.dataForm.icon,
                'url': this.dataForm.url,
                'intro': this.dataForm.intro,
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
