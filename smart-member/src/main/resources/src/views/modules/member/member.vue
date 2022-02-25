<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('member:member:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('member:member:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="uid"
        header-align="center"
        align="center"
        label="主键uid">
      </el-table-column>
      <el-table-column
        prop="nickName"
        header-align="center"
        align="center"
        label="呢称">
      </el-table-column>
      <el-table-column
        prop="email"
        header-align="center"
        align="center"
        label="登录账号">
      </el-table-column>
      <el-table-column
        prop="passWord"
        header-align="center"
        align="center"
        label="密码">
      </el-table-column>
      <el-table-column
        prop="gender"
        header-align="center"
        align="center"
        label="性别 0 = 男 1= 女">
      </el-table-column>
      <el-table-column
        prop="phone"
        header-align="center"
        align="center"
        label="手机号">
      </el-table-column>
      <el-table-column
        prop="avatar"
        header-align="center"
        align="center"
        label="头像地址">
      </el-table-column>
      <el-table-column
        prop="intro"
        header-align="center"
        align="center"
        label="个人介绍">
      </el-table-column>
      <el-table-column
        prop="fans"
        header-align="center"
        align="center"
        label="粉丝数">
      </el-table-column>
      <el-table-column
        prop="follow"
        header-align="center"
        align="center"
        label="关注数">
      </el-table-column>
      <el-table-column
        prop="score"
        header-align="center"
        align="center"
        label="积分">
      </el-table-column>
      <el-table-column
        prop="birthday"
        header-align="center"
        align="center"
        label="生日">
      </el-table-column>
      <el-table-column
        prop="gitee"
        header-align="center"
        align="center"
        label="gitee地址">
      </el-table-column>
      <el-table-column
        prop="github"
        header-align="center"
        align="center"
        label="github地址">
      </el-table-column>
      <el-table-column
        prop="os"
        header-align="center"
        align="center"
        label="访问系统">
      </el-table-column>
      <el-table-column
        prop="uuid"
        header-align="center"
        align="center"
        label="平台uuid">
      </el-table-column>
      <el-table-column
        prop="qqNumber"
        header-align="center"
        align="center"
        label="QQ号码">
      </el-table-column>
      <el-table-column
        prop="commentStatus"
        header-align="center"
        align="center"
        label="0 = 正常 1 = 不可评论">
      </el-table-column>
      <el-table-column
        prop="browser"
        header-align="center"
        align="center"
        label="访问浏览器">
      </el-table-column>
      <el-table-column
        prop="userTag"
        header-align="center"
        align="center"
        label="用户的标签 0 = 普通用户">
      </el-table-column>
      <el-table-column
        prop="status"
        header-align="center"
        align="center"
        label="用户状态 0 = 正常">
      </el-table-column>
      <el-table-column
        prop="lastLoginTime"
        header-align="center"
        align="center"
        label="上一次登录的时间">
      </el-table-column>
      <el-table-column
        prop="lastLoginIp"
        header-align="center"
        align="center"
        label="上一次登录的ip">
      </el-table-column>
      <el-table-column
        prop="updatedTime"
        header-align="center"
        align="center"
        label="">
      </el-table-column>
      <el-table-column
        prop="createdTime"
        header-align="center"
        align="center"
        label="">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.uid)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.uid)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './member-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/member/member/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      },
      // 多选
      selectionChangeHandle (val) {
        this.dataListSelections = val
      },
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.uid
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/member/member/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
