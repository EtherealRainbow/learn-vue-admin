<template>
  <table style="width: 100%;">
    <tr style="float:left;">
      <td>
        <Input v-model="inputName" placeholder="系统名称" style="width: 200px"/>
      </td>
      <td>
        <Button type="info" @click="select">查询</Button>
        <Button type="error" @click="reset">重置</Button>
      </td>
      <td>
        <Button style="right: 20px;" type="primary" @click='add("true")'>新增</Button>
      </td>
    </tr>
    <tr>
      <Table :columns="columns" :data="tableData" border></Table>
      <div v-if="tableData.length" class="pageBox" style="margin-top: 5px;">
        <Page :page-size="pageSize" :page-size-opts="[5,10,15,20,30,40]" :total="total"
              show-elevator show-sizer @on-page-size-change="pageSizeChange" @on-change="pageChange"/>
      </div>
    </tr>
  </table>
</template>

<script>
export default {
  name: "userList",
  data() {
    return {
      columns: [{
        type: 'index',
        width: 60,
        align: 'center'
      }, {
        title: '姓名',
        align: 'center',
        key: 'userName'
      }, {
        title: '身份证号',
        align: 'center',
        key: 'card'
      }, {
        title: '密码',
        align: 'center',
        key: 'password',
        render: (h, params) => {
          let password = "****"
          return h('span', password);
        }
      }, {
        title: '状态',
        align: 'center',
        key: 'state'
      }, {
        title: '操作',
        key: 'action',
        align: 'center',
        width: 200,
        fixed: 'right',
        render: (h, params) => {
          return h('div', [
            h(
              'Button', {
                props: {
                  type: 'primary',
                  size: 'small',
                  icon: 'ios-create-outline'
                },
                style: {
                  marginRight: '5px'
                },
                on: {
                  click: () => {
                    this.edit(params.row)
                  }
                }
              },
              '编辑'
            ),
            h(
              'Button', {
                props: {
                  type: 'error',
                  size: 'small',
                  icon: 'md-trash'
                },
                on: {
                  click: () => {
                    this.remove(params.row)
                  }
                }
              },
              '删除'
            )
          ])
        }
      }],
      tableData: [],
      page: 0,
      pageSize: 10,
      total: 0,
      selected: [],
      curPageSelected: [],
      inputName: '',
      modalConfig_table: { //弹窗属性设置数据
        window_udpTable: false, //更新table弹窗的显示状态  用于全局控制
        okText: '确定',
        cancelText: '取消',
        title: 'FTP链接信息'
      },
      selectIndex: 0,
      modalType: "add"
    }
  },
  created() {

  },
  methods: {
    //加载数据
    loadData() {

    },
    //查询
    select() {

    },
    //重置
    reset() {

    },
    //新增
    add() {

    },
    pageChange(page) {
      this.page = page - 1;
      this.select();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.page = 0;
      this.select();
    },
  }
}
</script>

<style scoped>

</style>
