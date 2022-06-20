<template>
  <div>
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


    <Modal v-model="modalConfig_table.window_udpTable" :footer-hide=true v-bind:title="modalConfig_table.title"
           v-bind:ok-text='modalConfig_table.okText' v-bind:cancel-text='modalConfig_table.cancelText' width="700">
      <!--  -->
      <Form ref="menuForm" :model="menuForm" label-position="right" :label-width="80">
        <Row>
          <Col span="12">
            <FormItem label="类型">
              <RadioGroup v-model="menuForm.type">
                <Radio label="0">
                  <span>菜单</span>
                </Radio>
                <Radio label="1">
                  <span>按钮</span>
                </Radio>
              </RadioGroup>
            </FormItem>
          </Col>
          <Col span="12">
            <FormItem label="排序">
              <InputNumber style="width: 254px;" :min="1" v-model="menuForm.sort"></InputNumber>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="名称">
              <Input v-model="menuForm.name"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="上级菜单">
<!--              <el-select v-model="selectTree.treeValue" placeholder="请选择">-->
<!--                <el-option-->
<!--                    v-for="item in selectTree.treeData"-->
<!--                    :key="item.value"-->
<!--                    :label="item.label"-->
<!--                    :value="item.value">-->
<!--                  <span style="float: left">{{ item.label }}</span>-->
<!--                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>-->
<!--                </el-option>-->
<!--              </el-select>-->
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="路由">
              <Input v-model="menuForm.url"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="授权标识">
              <Input v-model="menuForm.permissions" placeholder="多个用逗号分隔，如：sys:user:list,sys:user:save"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="图标">
              <Input v-model="menuForm.icon"></Input>
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

export default {
  name: "menuList",
  data() {
    return {
      columns: [
          {
        type: 'index',
        width: 60,
        align: 'center'
      }, {
        title: '名称',
        align: 'center',
        key: 'name'
      }, {
        title: '图标',
        align: 'center',
        key: 'icon'
      }, {
        title: '类型',
        align: 'center',
        key: 'type'
      }, {
        title: '排序',
        align: 'center',
        key: 'sort'
      }, {
        title: '路由',
        align: 'center',
        key: 'url'
      }, {
        title: '授权标识',
        align: 'center',
        key: 'permissions'
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
        title: '菜单表单'
      },
      menuForm:{
        type:0,
        ico:'',
        url:'',
        name:'',
        permissions:'',
        sort:0,
      },
      selectTree:{
        expandOnClickNode: true,
        options:[],
        defaultProps:{
          children: 'children',
          label: 'label'
        },
        treeValue:1,
        treeData: [{
          value: 'Beijing',
          label: '北京'
        }, {
          value: 'Shanghai',
          label: '上海'
        }, {
          value: 'Nanjing',
          label: '南京'
        }, {
          value: 'Chengdu',
          label: '成都'
        }, {
          value: 'Shenzhen',
          label: '深圳'
        }, {
          value: 'Guangzhou',
          label: '广州'
        }]
      },
      selectIndex: 0,
      modalType: "add"
    }
  },
  created() {

  },
  methods: {
    // 四级菜单
    formatData(data){
      let options = [];
      data.forEach((item,key) => {
        options.push({label:item.label,value:item.id});
        if(item.children){
          item.children.forEach((items,keys) => {
            options.push({label:items.label,value:items.id});
            if(items.children){
              items.children.forEach((itemss,keyss) => {
                options.push({label:itemss.label,value:itemss.id});
                if(itemss.children){
                  itemss.children.forEach((itemsss,keysss) => {
                    options.push({label:itemsss.label,value:itemsss.id});
                  });
                }
              });
            }
          });
        }
      });
      return options;
    },
    //树点击事件
    handleNodeClick(data) {
      console.log(data);
    },
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
    add(flag) {
      //打开或者关闭窗口
      // this.resetForm();
      this.modalType = "add";
      this.modalConfig_table.window_udpTable = flag;
      // this.select();
    },
    ok(){

    },
    cancel(){
      this.add(false);
    },
    pageChange(page) {
      this.page = page - 1;
      this.select();
    },
    pageSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.page = 0;
      this.select();
    }
  }
}
</script>

<style scoped>
.button_group {
  margin-left: 0px !important;
  text-align: center;
}
.main-select-el-tree .el-tree-node .is-current > .el-tree-node__content{font-weight: bold; color: #409eff;}
.main-select-el-tree .el-tree-node.is-current > .el-tree-node__content{font-weight: bold; color: #409eff;}
</style>
