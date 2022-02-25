<template>
  <el-dialog
    :title="!dataForm.uid ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="呢称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder="呢称"></el-input>
    </el-form-item>
    <el-form-item label="登录账号" prop="email">
      <el-input v-model="dataForm.email" placeholder="登录账号"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="passWord">
      <el-input v-model="dataForm.passWord" placeholder="密码"></el-input>
    </el-form-item>
    <el-form-item label="性别 0 = 男 1= 女" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别 0 = 男 1= 女"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="phone">
      <el-input v-model="dataForm.phone" placeholder="手机号"></el-input>
    </el-form-item>
    <el-form-item label="头像地址" prop="avatar">
      <el-input v-model="dataForm.avatar" placeholder="头像地址"></el-input>
    </el-form-item>
    <el-form-item label="个人介绍" prop="intro">
      <el-input v-model="dataForm.intro" placeholder="个人介绍"></el-input>
    </el-form-item>
    <el-form-item label="粉丝数" prop="fans">
      <el-input v-model="dataForm.fans" placeholder="粉丝数"></el-input>
    </el-form-item>
    <el-form-item label="关注数" prop="follow">
      <el-input v-model="dataForm.follow" placeholder="关注数"></el-input>
    </el-form-item>
    <el-form-item label="积分" prop="score">
      <el-input v-model="dataForm.score" placeholder="积分"></el-input>
    </el-form-item>
    <el-form-item label="生日" prop="birthday">
      <el-input v-model="dataForm.birthday" placeholder="生日"></el-input>
    </el-form-item>
    <el-form-item label="gitee地址" prop="gitee">
      <el-input v-model="dataForm.gitee" placeholder="gitee地址"></el-input>
    </el-form-item>
    <el-form-item label="github地址" prop="github">
      <el-input v-model="dataForm.github" placeholder="github地址"></el-input>
    </el-form-item>
    <el-form-item label="访问系统" prop="os">
      <el-input v-model="dataForm.os" placeholder="访问系统"></el-input>
    </el-form-item>
    <el-form-item label="平台uuid" prop="uuid">
      <el-input v-model="dataForm.uuid" placeholder="平台uuid"></el-input>
    </el-form-item>
    <el-form-item label="QQ号码" prop="qqNumber">
      <el-input v-model="dataForm.qqNumber" placeholder="QQ号码"></el-input>
    </el-form-item>
    <el-form-item label="0 = 正常 1 = 不可评论" prop="commentStatus">
      <el-input v-model="dataForm.commentStatus" placeholder="0 = 正常 1 = 不可评论"></el-input>
    </el-form-item>
    <el-form-item label="访问浏览器" prop="browser">
      <el-input v-model="dataForm.browser" placeholder="访问浏览器"></el-input>
    </el-form-item>
    <el-form-item label="用户的标签 0 = 普通用户" prop="userTag">
      <el-input v-model="dataForm.userTag" placeholder="用户的标签 0 = 普通用户"></el-input>
    </el-form-item>
    <el-form-item label="用户状态 0 = 正常" prop="status">
      <el-input v-model="dataForm.status" placeholder="用户状态 0 = 正常"></el-input>
    </el-form-item>
    <el-form-item label="上一次登录的时间" prop="lastLoginTime">
      <el-input v-model="dataForm.lastLoginTime" placeholder="上一次登录的时间"></el-input>
    </el-form-item>
    <el-form-item label="上一次登录的ip" prop="lastLoginIp">
      <el-input v-model="dataForm.lastLoginIp" placeholder="上一次登录的ip"></el-input>
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
          nickName: '',
          email: '',
          passWord: '',
          gender: '',
          phone: '',
          avatar: '',
          intro: '',
          fans: '',
          follow: '',
          score: '',
          birthday: '',
          gitee: '',
          github: '',
          os: '',
          uuid: '',
          qqNumber: '',
          commentStatus: '',
          browser: '',
          userTag: '',
          status: '',
          lastLoginTime: '',
          lastLoginIp: '',
          updatedTime: '',
          createdTime: ''
        },
        dataRule: {
          nickName: [
            { required: true, message: '呢称不能为空', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '登录账号不能为空', trigger: 'blur' }
          ],
          passWord: [
            { required: true, message: '密码不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别 0 = 男 1= 女不能为空', trigger: 'blur' }
          ],
          phone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' }
          ],
          avatar: [
            { required: true, message: '头像地址不能为空', trigger: 'blur' }
          ],
          intro: [
            { required: true, message: '个人介绍不能为空', trigger: 'blur' }
          ],
          fans: [
            { required: true, message: '粉丝数不能为空', trigger: 'blur' }
          ],
          follow: [
            { required: true, message: '关注数不能为空', trigger: 'blur' }
          ],
          score: [
            { required: true, message: '积分不能为空', trigger: 'blur' }
          ],
          birthday: [
            { required: true, message: '生日不能为空', trigger: 'blur' }
          ],
          gitee: [
            { required: true, message: 'gitee地址不能为空', trigger: 'blur' }
          ],
          github: [
            { required: true, message: 'github地址不能为空', trigger: 'blur' }
          ],
          os: [
            { required: true, message: '访问系统不能为空', trigger: 'blur' }
          ],
          uuid: [
            { required: true, message: '平台uuid不能为空', trigger: 'blur' }
          ],
          qqNumber: [
            { required: true, message: 'QQ号码不能为空', trigger: 'blur' }
          ],
          commentStatus: [
            { required: true, message: '0 = 正常 1 = 不可评论不能为空', trigger: 'blur' }
          ],
          browser: [
            { required: true, message: '访问浏览器不能为空', trigger: 'blur' }
          ],
          userTag: [
            { required: true, message: '用户的标签 0 = 普通用户不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '用户状态 0 = 正常不能为空', trigger: 'blur' }
          ],
          lastLoginTime: [
            { required: true, message: '上一次登录的时间不能为空', trigger: 'blur' }
          ],
          lastLoginIp: [
            { required: true, message: '上一次登录的ip不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/member/member/info/${this.dataForm.uid}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.nickName = data.member.nickName
                this.dataForm.email = data.member.email
                this.dataForm.passWord = data.member.passWord
                this.dataForm.gender = data.member.gender
                this.dataForm.phone = data.member.phone
                this.dataForm.avatar = data.member.avatar
                this.dataForm.intro = data.member.intro
                this.dataForm.fans = data.member.fans
                this.dataForm.follow = data.member.follow
                this.dataForm.score = data.member.score
                this.dataForm.birthday = data.member.birthday
                this.dataForm.gitee = data.member.gitee
                this.dataForm.github = data.member.github
                this.dataForm.os = data.member.os
                this.dataForm.uuid = data.member.uuid
                this.dataForm.qqNumber = data.member.qqNumber
                this.dataForm.commentStatus = data.member.commentStatus
                this.dataForm.browser = data.member.browser
                this.dataForm.userTag = data.member.userTag
                this.dataForm.status = data.member.status
                this.dataForm.lastLoginTime = data.member.lastLoginTime
                this.dataForm.lastLoginIp = data.member.lastLoginIp
                this.dataForm.updatedTime = data.member.updatedTime
                this.dataForm.createdTime = data.member.createdTime
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
              url: this.$http.adornUrl(`/member/member/${!this.dataForm.uid ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'uid': this.dataForm.uid || undefined,
                'nickName': this.dataForm.nickName,
                'email': this.dataForm.email,
                'passWord': this.dataForm.passWord,
                'gender': this.dataForm.gender,
                'phone': this.dataForm.phone,
                'avatar': this.dataForm.avatar,
                'intro': this.dataForm.intro,
                'fans': this.dataForm.fans,
                'follow': this.dataForm.follow,
                'score': this.dataForm.score,
                'birthday': this.dataForm.birthday,
                'gitee': this.dataForm.gitee,
                'github': this.dataForm.github,
                'os': this.dataForm.os,
                'uuid': this.dataForm.uuid,
                'qqNumber': this.dataForm.qqNumber,
                'commentStatus': this.dataForm.commentStatus,
                'browser': this.dataForm.browser,
                'userTag': this.dataForm.userTag,
                'status': this.dataForm.status,
                'lastLoginTime': this.dataForm.lastLoginTime,
                'lastLoginIp': this.dataForm.lastLoginIp,
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
