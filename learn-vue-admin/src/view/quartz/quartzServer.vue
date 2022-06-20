<template>
  <div>
    <table style="width: 100%;">
      <tr style="float:left;">
        <td>
          <Input placeholder="系统名称" v-model="inputName" style="width: 200px" />
        </td>
        <td>
          <Button type="info" @click="select">查询</Button>
          <Button type="error" @click="reset">重置</Button>
        </td>
        <td>
          <Button type="primary" style="right: 20px;" @click='openOrCloseModal("true")'>新增</Button>
        </td>
      </tr>
      <tr>
        <Table border :columns="columns" :data="tableData"></Table>
        <div class="pageBox" style="margin-top: 5px;" v-if="tableData.length">
          <Page @on-page-size-change="pageSizeChange" @on-change="pageChange" :total="total"
                :page-size="pageSize" show-sizer :page-size-opts="[5,10,15,20,30,40]" show-elevator />
        </div>
      </tr>
    </table>
    <!-- 新增弹窗 -->
    <Modal v-model="modalConfig_table.window_udpTable" :footer-hide=true v-bind:title="modalConfig_table.title"
           v-bind:ok-text='modalConfig_table.okText' v-bind:cancel-text='modalConfig_table.cancelText' width="700">
      <!--  -->
      <Form ref="ftpForm" :model="ftpForm" label-position="right" :label-width="80">
        <Row>
          <Col span="24">
            <FormItem label="系统名称">
              <Input v-model="ftpForm.systemName"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <FormItem label="默认路径">
              <Input v-model="ftpForm.path"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="类型">
              <Input v-model="ftpForm.type" readonly></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <FormItem label="IP地址">
              <Input v-model="ftpForm.ipAddress"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="端口">
              <Input v-model="ftpForm.portAddress"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="12">
            <FormItem label="用户名">
              <Input v-model="ftpForm.userName"></Input>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="密码">
              <Input type="password" v-model="ftpForm.password"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem class="button_group">
              <!-- 提交的单击事件  在下面的方法里面写好 -->
              <Button type="primary" @click="ok()">提交</Button>
              <Button @click="cancel()" style="margin-left: 8px">取消</Button>
            </FormItem>
          </Col>
        </Row>

      </Form>
    </Modal>
  </div>

</template>

<script>
import * as quartz from '@/api/quartz'
import {
  Message
} from 'iview';
export default {
  name:"quartzServer",
  data() {
    return {
      columns: [{
        type: 'index',
        width: 60,
        align: 'center'
      }, {
        title: '系统名称',
        align: 'center',
        width: 200,
        key: 'systemName'
      },
        {
          title: 'IP地址',
          align: 'center',
          width: 200,
          key: 'ipAddress'
        },
        {
          title: '端口',
          align: 'center',
          width: 120,
          key: 'portAddress'
        },
        {
          title: '用户名',
          align: 'center',
          width: 200,
          key: 'userName'
        },
        {
          title: '密码',
          align: 'center',
          width: 150,
          key: 'password',
          render: (h, params) => {
            let password = "****"
            return h('span', password);
          }
        },
        {
          title: '状态',
          width: 150,
          align: 'center',
          key: 'state'
        },
        {
          title: '文件路径',
          align: 'center',
          width: 250,
          key: 'path'
        },
        {
          title: '文件类型',
          align: 'center',
          width: 100,
          key: 'type'
        },
        {
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
        },
      ],
      tableData: [],
      page: 0,
      pageSize: 10,
      total: 0,
      selected: [],
      curPageSelected: [],
      ftpForm: {
        systemName: 'FTP测试',
        ipAddress: '192.168.1.201',
        portAddress: '21',
        userName: 'sckj',
        password: 'win2012-password',
        path: '/',
        type: 'FTP',
      },
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
  // 创建完毕状态(里面是操作)
  created() {
  },
  methods: {
    openOrCloseModal(flag) {
      //打开或者关闭窗口
      window.open("http://localhost:8888/preview")
      // quartz.testPdf({}).then(res => {
      //   console.dir(res);
      // });
    }
  }
}
</script>

<style>
.button_group>button {
  margin-left: 0px !important;
  text-align: center;
}
</style>
