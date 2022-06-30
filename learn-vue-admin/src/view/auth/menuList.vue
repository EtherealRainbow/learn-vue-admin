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
              <Input v-model="menuForm.name"  placeholder="请输入名称"></Input>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="上级菜单">
              <!--              <Treeselect v-model="treeValue" :multiple="false" :options="treeData" />-->
              <!--              <TreeSelect v-model="treeSelect.treeValue" :data="treeSelect.treeData"  />-->
              <treeSelect
                  :close-on-select="selectTree.closeOnSelect"
                  v-model="selectTree.treeValue"
                  :options="selectTree.treeData"
                  @select="getSelect"
                  placeholder="请选择上级菜单"
              ></treeSelect>
            </FormItem>
          </Col>
        </Row>
        <Row>
          <Col span="24">
            <FormItem label="路由">
              <Input v-model="menuForm.url" placeholder="请输入路由"></Input>
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
              <Input v-model="menuForm.icon" placeholder="请输入图标"></Input>
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
import treeSelect  from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  components: {treeSelect },
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
      menuForm: {
        type: 0,
        ico: '',
        url: '',
        name: '',
        permissions: '',
        sort: 0,
        parentId:''
      },
      selectTree: {
        multiple: true,//是否多选
        clearable: true,//是否显示重置值的“×”按钮。
        searchable: true,//设置 true为允许选择多个选项（又名多重选择模式）。
        disabled: false,//	是否启用搜索功能。
        openOnClick: true,//单击控件时是否自动打开菜单。
        openOnFocus: false,//控件集中时是否自动打开菜单。
        clearOnSelect: true,//选择选项后是否关闭菜单。仅在时使用 :multiple="true".
        alwaysOpen: false,//菜单是否应始终打开。
        appendToBody: false,//将菜单追加到 <body />
        treeValue:null,
        treeData: [],
      },
      selectIndex: 0,
      modalType: "add"
    }
  },
  created() {
    this.loadData();
  },
  methods: {
    //加载数据
    loadData() {
      //测试数据
      let data = [
        {
          id: 'a',
          label: 'a',
          children: [{
            id: 'aa',
            label: 'aa',
          }, {
            id: 'ab',
            label: 'ab',
          }],
        }, {
          id: 'b',
          label: 'b',
        }, {
          id: 'c',
          label: 'c',
        }
      ]
      this.selectTree.treeData = data;
    },
    getSelect(node, instanceId){
      this.menuForm.parentId = node.id
      console.dir(node);
      console.dir(instanceId);
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
      this.resetForm();
      this.modalType = "add";
      this.modalConfig_table.window_udpTable = flag;
      // this.select();
    },
    resetForm(){
      this.menuForm={
        type: 0,
        ico: '',
        url: '',
        name: '',
        permissions: '',
        sort: 0,
        parentId:''
      };
    },
    ok() {

    },
    cancel() {
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

.main-select-el-tree .el-tree-node .is-current > .el-tree-node__content {
  font-weight: bold;
  color: #409eff;
}

.main-select-el-tree .el-tree-node.is-current > .el-tree-node__content {
  font-weight: bold;
  color: #409eff;
}
</style>
