<template>
  <table style="width: 100%;">
    <tr style="float:left;">
      <td>
        <Input v-model="table.inputName" placeholder="系统名称" style="width: 200px"/>
      </td>
      <td>
        <Button type="info" @click="select">查询</Button>
        <Button type="error" @click="reset">重置</Button>
      </td>
      <td>
        <Button style="right: 20px;" type="primary" @click='handleAdd("true")'>新增</Button>
      </td>
    </tr>
    <tr>
      <Table :columns="table.columns" :data="table.tableData" border class="table">
        <template #name="{ row }">
          <strong>{{ row.name }}</strong>
        </template>
        <template #action="{ row, index }">
          <Button type="primary" size="small" style="margin-right: 5px" @click="handleAdd(index)">View</Button>
          <Button type="error" size="small" @click="remove(index)">Delete</Button>
        </template>
      </Table>
      <div v-if="table.tableData.length" class="pageBox" style="margin-top: 5px;">
        <Page :page-size="table.pageSize" :page-size-opts="[5,10,15,20,30,40]" :total="table.total"
              show-elevator show-sizer @on-page-size-change="pageSizeChange" @on-change="pageChange"/>
      </div>
    </tr>

  </table>
</template>

<script>
// import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild } from "@/api/system/dept";
import treeSelect  from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

export default {
  name: "Dept",
  // dicts: ['sys_normal_disable'],
  components: { treeSelect },
  data() {
    return {
      table:{
        inputName:'',
        columns:[
          {
            title: 'Name',
            resizable:true,
            slot: 'name'
          },
          {
            title: 'Age',
            resizable:true,
            key: 'age'
          },
          {
            title: 'Address',
            resizable:true,
            key: 'address'
          },
          {
            title: 'Action',
            slot: 'action',
            resizable:true,
            width: 150,
            align: 'center'
          }],
        tableData:[
          {
            name: 'John Brown',
            age: 18,
            address: 'New York No. 1 Lake Park'
          },
          {
            name: 'Jim Green',
            age: 24,
            address: 'London No. 1 Lake Park'
          },
          {
            name: 'Joe Black',
            age: 30,
            address: 'Sydney No. 1 Lake Park'
          },
          {
            name: 'Jon Snow',
            age: 26,
            address: 'Ottawa No. 2 Lake Park'
          }
        ],
        page: 0,
        pageSize: 10,
        total: 0,
        selected: [],
        curPageSelected: []
      }
    }
  },
  created() {
  },
  methods: {
    /** 查询数据列表 **/
    getTableList(){

    },
    /** 查询  **/
    select(){

    },
    /** 重置 **/
    reset(){

    },
    /** 添加 **/
    handleAdd(index){

    },
    remove(index){

    },
    /**分页大小**/
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
};
</script>
<style>
.table table{
  table-layout: auto;
  width: 100% !important;

}
</style>
